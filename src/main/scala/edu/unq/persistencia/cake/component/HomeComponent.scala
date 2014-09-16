package edu.unq.persistencia.cake.component

trait HomeComponent[T] {
  def locator : Locator
  def updater : Updater
  trait Locator {
    def findAll: Seq[T]
    def get(id:Long):T
  }
  trait Updater {
    def save(entity: T)
  }
}