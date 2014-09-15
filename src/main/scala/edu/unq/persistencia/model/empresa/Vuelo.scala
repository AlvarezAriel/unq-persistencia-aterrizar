package edu.unq.persistencia.model.empresa

import scala.beans.BeanProperty

class Vuelo (
              @BeanProperty var tramos:Seq[Tramo]
) extends Identificable {
  def esDirecto: Boolean = tramos.length == 1
}
