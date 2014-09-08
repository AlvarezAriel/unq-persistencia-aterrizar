package edu.unq.persistencia.services

import edu.unq.persistencia.bussinessExceptions._
import edu.unq.persistencia.bussinessExceptions.UsuarioYaExisteException
import edu.unq.persistencia.bussinessExceptions.ValidacionException
import edu.unq.persistencia.cake.service.DefaultServiceComponent
import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.homes.{UsuarioHome, Home}
import edu.unq.persistencia.mailing.{CodigoValidacionEmail, Mail, EnviadorDeMails}
import edu.unq.persistencia.model.login.UsuarioEntity

abstract class UsuarioService {

  implicit val usuarioHome: UsuarioHome
  implicit val enviadorDeMails: EnviadorDeMails

  def registrarUsuario ( usuarioNuevo:UsuarioEntity) : UsuarioEntity = {
    usuarioHome.findUserByUsername(usuarioNuevo.username) match {
      case Some(_) => throw UsuarioYaExisteException
      case None =>
        val usuarioCreado = usuarioHome.crearNuevo(usuarioNuevo)
        enviadorDeMails.enviarMail(
          Mail(
            from = "alguien@noreply.com",
            to = usuarioCreado.email,
            subject = "Validación de cuenta",
            body = CodigoValidacionEmail.buildBody(usuarioCreado)
          )
        )
        usuarioCreado
    }
  }

  def validarCuenta (codigoValidacion:String) = {
    usuarioHome.findUserByCodigoDeValidacion(codigoValidacion) match {
      case Some(usuario) => usuario.validado match {
        case false => usuarioHome.marcarComoValidado(usuario)
        case true => throw CodigoDeValidacionYaUtilizado
      }
      case None => throw ValidacionException
    }
  }

  def ingresarUsuario (userName:String, password:String) : UsuarioEntity = {
    findUserByNameAndPassword(userName, password)
  }

  def cambiarPassword (userName:String, password:String, nuevaPassword:String) = {
    val usuario = findUserByNameAndPassword(userName, password)
    if(usuario.password == nuevaPassword) throw NuevaPasswordInvalida
    usuarioHome.changePassword(usuario, nuevaPassword)
  }

  private def findUserByNameAndPassword(userName: String, password: String): UsuarioEntity = {
    usuarioHome.findUserByNameAndPassword(userName, password) match {
      case Some(usuario) => usuario.validado match {
        case true => usuario
        case false => throw UsuarioNoValidado
      }
      case None => throw UsuarioNoExiste
    }
  }
}
