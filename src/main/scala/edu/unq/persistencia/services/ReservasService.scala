package edu.unq.persistencia.services

import edu.unq.persistencia.model.login.UsuarioEntity
import edu.unq.persistencia.model.{Tramo, Asiento}
import edu.unq.persistencia.bussinessExceptions.{AsientoYaReservado, Assert}
import org.hibernate.Session
import org.hibernate.criterion.Restrictions
import scala.collection.JavaConversions._

class ReservasService {

  def reservarAsiento(usuario:UsuarioEntity, asientoId:Long)(implicit session:Session) = {

    val asiento  = session.createCriteria(classOf[Asiento])
        .add(Restrictions.isNull("pasajero"))
        .add(Restrictions.eq("id", asientoId)
    ).uniqueResult()

    Assert(asiento != null).using(AsientoYaReservado)
    asiento.asInstanceOf[Asiento].reservarPara(usuario)
  }

  def reservarAsientos(usuario:UsuarioEntity, idsAsientos:Seq[Long])(implicit session:Session) = {

    val asientos  = session.createCriteria(classOf[Asiento]).
        add(Restrictions.in("id", idsAsientos)).
        add(Restrictions.isNull("pasajero")).
    list.toSet.asInstanceOf[Set[Asiento]]

    Assert(asientos.size == idsAsientos.size).using(AsientoYaReservado)
    asientos.foreach(_.reservarPara(usuario))
  }

  def asientosDisponiblesPara(tramoID:Long)(implicit session:Session) = {
    session.createCriteria(classOf[Asiento]).add(Restrictions.eq("tramo.id", tramoID)).list.toSet.asInstanceOf[Set[Asiento]]
  }

}
