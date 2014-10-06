package edu.unq.persistencia.cake.component

import scala.collection.JavaConversions._
import edu.unq.persistencia.{SessionProviderComponent, DefaultSessionProviderComponent}
import edu.unq.persistencia.model.Entity
import org.hibernate.Transaction

trait Query[T] {
  def getResultList:Seq[T]
}

trait HomeComponentJPA[T <: Entity[_]] extends HomeComponent[T]  {
  this: SessionProviderComponent =>

  val clazz:Class[T]

  def locator = new LocatorJPA
  def updater = new UpdaterJPA

  def withTransaction[R](  operation:()=> R ):R = {
    val transaction: Transaction = sessionProvider.session.beginTransaction()
    try {
      val r = operation()
      transaction.commit()
      r
    }
    catch {
      case error:Throwable =>
        transaction.rollback()
        throw error
    }
  }

  class LocatorJPA extends Locator {

    def findAll = withTransaction { () => sessionProvider.session.createCriteria(clazz).list.asInstanceOf[List[T]]}

//    def myClassOf[Algo:ClassTag] = implicitly[ClassTag[Algo]].runtimeClass
    def get(id: Long): T = withTransaction { () =>
      sessionProvider.session.get(clazz, id).asInstanceOf[T]
    }
  }

  class UpdaterJPA extends Updater {


    def save(entity: T) = withTransaction { () => sessionProvider.session.saveOrUpdate(entity) }

    val deleteAll = withTransaction { () =>
      val hql:String  = String.format("delete from %s",clazz.getSimpleName)
      sessionProvider.session.createQuery(hql).executeUpdate()
    }

  }

}