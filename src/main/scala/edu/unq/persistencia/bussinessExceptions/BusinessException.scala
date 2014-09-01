package edu.unq.persistencia.bussinessExceptions

trait BusinessException extends Exception

case object  UsuarioYaExisteException extends BusinessException
case object  ValidacionException extends BusinessException
case object  CodigoDeValidacionYaUtilizado extends BusinessException
case object  UsuarioNoExiste extends BusinessException
case object  UsuarioNoValidado extends BusinessException
case object  NuevaPasswordInvalida extends BusinessException
case object  EnviarMailException extends BusinessException