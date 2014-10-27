package edu.unq.persistencia.bussinessExceptions

trait BusinessException extends Exception

case object  UsuarioYaExisteException extends BusinessException
case object  NoSeEncontroElusuarioException extends BusinessException
case object  ValidacionException extends BusinessException
case object  CodigoDeValidacionYaUtilizado extends BusinessException
case object  UsuarioNoExiste extends BusinessException
case object  UsuarioNoValidado extends BusinessException
case object  NuevaPasswordInvalida extends BusinessException
case object  EnviarMailException extends BusinessException
case object  AsientoYaReservado extends BusinessException

case class Assert(condition:Boolean){
  def otherwiseThrow(e:BusinessException) = if(!condition) throw e
}
