package edu.unq.persistencia.services

import edu.unq.persistencia.model.login.UsuarioEntity
import edu.unq.persistencia.model.Asiento
import edu.unq.persistencia.cake.component.HomeComponentJPA

abstract class ReservasService {

  implicit val asientosHome: HomeComponentJPA[Asiento]
  implicit val usuariosHome: HomeComponentJPA[UsuarioEntity]

  def reservarAsiento(usuario:UsuarioEntity, asiento:Asiento) = {
  }
}
