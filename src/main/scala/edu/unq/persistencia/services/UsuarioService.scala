package edu.unq.persistencia.services

import edu.unq.persistencia.bussinessExceptions._
import edu.unq.persistencia.bussinessExceptions.UsuarioYaExisteException
import edu.unq.persistencia.bussinessExceptions.ValidacionException
import edu.unq.persistencia.model.UsuarioEntity
import edu.unq.persistencia.cake.service.DefaultServiceComponent
import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.homes.{UsuarioHome, Home}

abstract class UsuarioService {

  implicit val usuarioHome: UsuarioHome

  def registrarUsuario ( usuarioNuevo:UsuarioEntity) = {
    //TODO: validar existencia
    usuarioHome.put(usuarioNuevo)
  }

  def validarCuenta (codigoValidacion:String) = {
    throw ValidacionException()
  }

  def ingresarUsuario (userName:String, password:String) : UsuarioEntity = {

    usuarioHome.findUserByNameAndPassword(userName, password) match {
      case Some(usuario) => usuario
      case None => throw UsuarioNoExiste()
    }
  }

  def cambiarPassword (userName:String, password:String, nuevaPassword:String) = {
    throw NuevaPasswordInvalida()
  }

}
