package edu.unq.persistencia.model

import scala.reflect.runtime.universe._

case class UsuarioEntity(nombre:String, apellido:String, username:String, email:String, fechaNacimiento:String /*TODO: mapear date*/) extends Entity[UsuarioEntity] {

}


//Nombre
//Apellido
//Nombre de Usuario (debe ser unique)
//E-Mail.
//Fecha de Nacimiento.
