package edu.unq.persistencia.model.login

import java.sql.ResultSet
import edu.unq.persistencia.model.Entity
import scala.beans.BeanProperty
import java.lang.reflect.Method

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
          //Las dos primeras columnas son el ID y el tableName, no nos interesan
          // (aunque vamos a tener que cambiar esto para que sea portable a diferentes engines)
          buildFromList( Range(3, resultSet.getMetaData.getColumnCount+1).map(
            it => resultSet.getObject(it)).toList
          )
        )
      case false => None
    }
  }

  def buildFromList(params:List[Any]) ={
    val contructor: Method = UsuarioEntity.getClass.getMethods.find(it => it.getName == "apply").get
    val parameters: List[AnyRef] = params map (_.asInstanceOf[AnyRef])
    contructor.invoke(UsuarioEntity, parameters: _*).asInstanceOf[UsuarioEntity]

  }

}