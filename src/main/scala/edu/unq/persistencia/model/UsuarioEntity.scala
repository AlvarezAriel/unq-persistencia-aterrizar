package edu.unq.persistencia.model

case class UsuarioEntity(
    nombre:String,
    apellido:String,
    username:String,
    email:String,
    fechaNacimiento:String /*TODO: mapear date*/
) extends Entity[UsuarioEntity] {




}
