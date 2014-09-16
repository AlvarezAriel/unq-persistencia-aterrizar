package edu.unq.persistencia.model.login

import java.sql.ResultSet
import edu.unq.persistencia.model.Entity
import scala.beans.BeanProperty

case class UsuarioEntity(
                          @BeanProperty var nombre:String,
                          @BeanProperty var apellido:String,
                          @BeanProperty var username:String,
                          @BeanProperty var email:String,
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
    UsuarioEntity.getClass.getMethods.find(it => it.getName == "apply").get.invoke(UsuarioEntity, params map (_.asInstanceOf[AnyRef]): _*).asInstanceOf[UsuarioEntity]

}