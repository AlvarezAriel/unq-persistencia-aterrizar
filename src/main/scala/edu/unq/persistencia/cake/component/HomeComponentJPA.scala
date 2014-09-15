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

  class LocatorJPA extends Locator {

    def findAll = sessionProvider.session.createCriteria(clazz).list.asInstanceOf[List[T]]

//    def myClassOf[Algo:ClassTag] = implicitly[ClassTag[Algo]].runtimeClass

  }

  class UpdaterJPA extends Updater {
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

    def save(entity: T) = withTransaction[Unit] { () => sessionProvider.session.saveOrUpdate(entity) }

  }

}