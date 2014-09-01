package edu.unq.persistencia.cake.service

import edu.unq.persistencia.cake.component.HomeComponent


trait ServiceComponent[T] {
  def service: Service

  trait Service {
    def findAll: Seq[T]
    def save(user: T)
  }
}

trait DefaultServiceComponent[T] extends ServiceComponent[T] {
  this: HomeComponent[T] =>

  def service = new DefaultService

  class DefaultService extends Service {
    def findAll = locator.findAll

    def save(user: T) {
      updater.save(user: T)
    }
  }
}