package edu.unq.persistencia.cake.component

import edu.unq.persistencia.model.Entity

trait Query[T] {
  def getResultList:Seq[T]
}

trait EntityManager {
  def createQuery[T](q:String, clazz:Class[T]):Query[T]
  def persist(something:Any)
}

class TrivialEntityManager extends EntityManager {
  override def persist(algo: Any): Unit = 0

  override def createQuery[T](q: String, clazz: Class[T]): Query[T] = new Query[T] {
    override def getResultList: Seq[T] = Seq.empty[T]
  }
}

trait HomeComponentJPA[T <: Entity[_]] extends HomeComponent[T] {
  val em: EntityManager
  def locator = new LocatorJPA(em)
  def updater = new UpdaterJPA(em)

  class LocatorJPA(val em: EntityManager) extends Locator {
    def findAll = Seq.empty[T] //em.createQuery("from User", classOf[T]).getResultList
  }
  class UpdaterJPA(val em: EntityManager) extends Updater {
    def save(entity: T) { em.persist(entity) }
  }
}