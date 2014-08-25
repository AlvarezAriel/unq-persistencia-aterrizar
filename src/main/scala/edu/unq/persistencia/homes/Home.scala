package edu.unq.persistencia.homes

import edu.unq.persistencia.model.Entity

trait Home[T <: Entity[_]] {
  def all: Seq[T] = Seq.empty[T]
}
