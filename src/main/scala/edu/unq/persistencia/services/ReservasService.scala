package edu.unq.persistencia.services

import edu.unq.persistencia.model.login.UsuarioEntity
import edu.unq.persistencia.model.{Tramo, Asiento}
import edu.unq.persistencia.bussinessExceptions.{AsientoYaReservado, Assert}
import org.hibernate.Session
import org.hibernate.criterion.Restrictions

class ReservasService {

  def reservarAsiento(usuario:UsuarioEntity, asiento:Asiento)(implicit session:Session) = {
    Assert(!asiento.reservado).using(AsientoYaReservado)
    asiento.reservarPara(usuario)
  }

  def reservarAsientos(usuario:UsuarioEntity, asientos:Seq[Asiento])(implicit session:Session) = {
    Assert(asientos.forall(_.reservado)).using(AsientoYaReservado)
    asientos.foreach(_.reservarPara(usuario))
  }

  def asientosDisponiblesPara(tramo:Tramo)(implicit session:Session) = {
    session.createCriteria(classOf[Asiento]).add(Restrictions.isNotNull("pasajero")).list
  }
}
