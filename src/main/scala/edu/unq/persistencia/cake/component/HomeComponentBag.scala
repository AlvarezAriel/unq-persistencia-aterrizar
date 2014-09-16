package edu.unq.persistencia.cake.component

trait HomeComponentBag[T] extends HomeComponent[T] {
  val container : scala.collection.mutable.Set[T]

  def locator = new LocatorBag(container)
  def updater = new UpdaterBag(container)

  class LocatorBag(val container: scala.collection.mutable.Set[T]) extends Locator {
    def findAll = container.toList
    def get(id:Long) = container.toSeq.take(id.toInt).head
  }

  class UpdaterBag(val container: scala.collection.mutable.Set[T]) extends Updater {
    def save(entity: T) { container.add(entity) }
  }

}