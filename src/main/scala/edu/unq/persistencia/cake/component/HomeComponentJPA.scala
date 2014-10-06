package edu.unq.persistencia.cake.component

import scala.collection.JavaConversions._
import edu.unq.persistencia.{SessionProvider, SessionProviderComponent, DefaultSessionProviderComponent}
import edu.unq.persistencia.model.Entity
import org.hibernate.{Session, Transaction}

trait Query[T] {
  def getResultList:Seq[T]
}

object DBAction {
  def withSession[R]( action:((Session)=> R) )(implicit sessionProvider:SessionProvider) = {
    val transaction: Transaction = sessionProvider.session.beginTransaction()
    try {
      val r = action.apply(sessionProvider.session)
      transaction.commit()
      r
    }
    catch {
      case error:Throwable =>
        transaction.rollback()
        throw error
    }
  }
}

trait HomeComponentJPA[T <: Entity[_]] {
  val clazz:Class[T]

  def locator = new LocatorJPA
  def updater = new UpdaterJPA

  class LocatorJPA {

    def findAll(implicit session:Session) = session.createCriteria(clazz).list.asInstanceOf[List[T]]

//    def myClassOf[Algo:ClassTag] = implicitly[ClassTag[Algo]].runtimeClass
    def get(id:Long)(implicit session:Session): T = session.get(clazz, id).asInstanceOf[T]
  }

  class UpdaterJPA {

    def save(entity: T)(implicit session:Session) = session.saveOrUpdate(entity)

    def deleteAll(implicit session:Session) = {
      val hql:String  = String.format("delete from %s",clazz.getSimpleName)
      session.createQuery(hql).executeUpdate()
    }

  }

}