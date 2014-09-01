package edu.unq.persistencia.model

import java.sql.ResultSet

case class UsuarioEntity(
    id:Int,
    nombre:String,
    apellido:String,
    username:String,
    email:String,
    fechaNacimiento:String, /*TODO: mapear date*/
    validado:Boolean,
    codigoValidacion: String,
    password:String
) extends Entity[UsuarioEntity] {

}

object UsuarioEntity {
  def construir(resultSet:ResultSet): Option[UsuarioEntity] = {
    resultSet.first() match {
      case true => Some(
          buildFromList( Range(2, resultSet.getMetaData.getColumnCount+1).map(
            it => resultSet.getObject(it)).toList
          )
        )
      case false => None
    }
  }

  def buildFromList(params:List[Any]) =
    UsuarioEntity.getClass.getMethods.head.invoke(UsuarioEntity, params map (_.asInstanceOf[AnyRef]): _*).asInstanceOf[UsuarioEntity]

}