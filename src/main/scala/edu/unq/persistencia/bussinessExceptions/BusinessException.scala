package edu.unq.persistencia.bussinessExceptions

trait BusinessException extends Exception

case class  UsuarioYaExisteException() extends BusinessException
case class  ValidacionException() extends BusinessException
case class  UsuarioNoExiste() extends BusinessException
case class  NuevaPasswordInvalida() extends BusinessException