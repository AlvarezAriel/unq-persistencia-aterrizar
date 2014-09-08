package edu.unq.persistencia.cake.component

import scala.collection.JavaConversions._
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.model.Entity

trait Query[T] {
  def getResultList:Seq[T]
}

trait HomeComponentJPA[T <: Entity[_]] extends HomeComponent[T] with DefaultSessionProviderComponent {
  val clazz:Class[T]

  def locator = new LocatorJPA
  def updater = new UpdaterJPA

  class LocatorJPA extends Locator {

    def findAll = sessionProvider.session.createCriteria(clazz).list.asInstanceOf[List[T]]

//    def myClassOf[Algo:ClassTag] = implicitly[ClassTag[Algo]].runtimeClass

  }

  class UpdaterJPA extends Updater {
    def save(entity: T) { sessionProvider.em.persist(entity) }
  }

}