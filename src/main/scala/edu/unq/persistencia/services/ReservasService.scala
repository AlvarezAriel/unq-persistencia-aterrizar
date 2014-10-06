package edu.unq.persistencia.services

import edu.unq.persistencia.model.login.UsuarioEntity
import edu.unq.persistencia.model.{Tramo, Asiento}
import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.bussinessExceptions.{AsientoYaReservado, Assert}
import scala.collection.JavaConversions._

abstract class ReservasService {

  implicit val asientosHome: HomeComponentJPA[Asiento]
  implicit val usuariosHome: HomeComponentJPA[UsuarioEntity]

  def reservarAsiento(usuario:UsuarioEntity, asiento:Asiento) = {
    Assert(!asiento.reservado).using(AsientoYaReservado)
    asiento.pasajero = usuario
  }

  def reservarAsientos(usuario:UsuarioEntity, asientos:Seq[Asiento]){
    Assert(asientos.forall(_.reservado)).using(AsientoYaReservado)
    asientos.foreach(_.pasajero=usuario)
  }

  def asientosDisponiblesPara(tramo:Tramo){
    tramo.asientos.filter(_.libre)
  }
}
