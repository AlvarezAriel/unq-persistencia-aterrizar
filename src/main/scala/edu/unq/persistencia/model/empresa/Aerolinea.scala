package edu.unq.persistencia.model.empresa

import scala.beans.BeanProperty

class Aerolinea(
   @BeanProperty var vuelos:Seq[Vuelo]
) extends Identificable {

//  def getVuelos = vuelos
//  def setVuelos(vuelos:Seq[Vuelo]) = this.vuelos = vuelos
}
