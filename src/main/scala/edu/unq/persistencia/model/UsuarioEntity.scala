package edu.unq.persistencia.model

import java.sql.ResultSet

case class UsuarioEntity(
    id:Int = 0,
    nombre:String = "",
    apellido:String = "",
    username:String = "",
    email:String = "",
    fechaNacimiento:String = "", /*TODO: mapear date*/
    validado:Boolean = false,
    codigoValidacion: String = "",
    password:String = ""
) extends Entity[UsuarioEntity] {}

object UsuarioEntity {
  def apply(resultSet:ResultSet): Option[UsuarioEntity] = {
    resultSet.first() match {
      case true => Some(
          UsuarioEntity().buildFromList( Range(0, resultSet.getMetaData.getColumnCount).map( it => resultSet.getObject(it)).toList)
        )
      case false => None
    }
  }
}