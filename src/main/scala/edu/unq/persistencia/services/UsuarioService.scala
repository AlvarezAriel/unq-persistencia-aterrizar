package edu.unq.persistencia.services

import edu.unq.persistencia.bussinessExceptions._
import edu.unq.persistencia.bussinessExceptions.UsuarioYaExisteException
import edu.unq.persistencia.bussinessExceptions.ValidacionException
import edu.unq.persistencia.model.UsuarioEntity

class UsuarioService {

  def registrarUsuario ( usuarioNuevo:UsuarioEntity) = {
    throw UsuarioYaExisteException()
  }

  def validarCuenta (codigoValidacion:String) = {
    throw ValidacionException()
  }

  def ingresarUsuario (userName:String, password:String) : UsuarioEntity = {
    throw UsuarioNoExiste()
  }

  def cambiarPassword (userName:String, password:String, nuevaPassword:String) = {
    throw NuevaPasswordInvalida()
  }

}
