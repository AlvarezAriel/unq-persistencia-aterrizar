package edu.unq.persistencia.model.login

import java.sql.ResultSet
import edu.unq.persistencia.model.{Asiento, Entity}
import scala.beans.BeanProperty
import java.lang.reflect.Method
import org.joda.time.DateTime

class UsuarioEntity extends Entity[UsuarioEntity] {
  @BeanProperty var nombre:String = ""
  @BeanProperty var apellido:String = ""
  @BeanProperty var username:String = ""
  @BeanProperty var email:String = ""
  @BeanProperty var fechaNacimiento:DateTime = DateTime.now()
  @BeanProperty var validado:Boolean = _
  @BeanProperty var codigoValidacion: String = _
  @BeanProperty var password:String = _
}

object UsuarioEntity {
  def apply(nombre:String,
            apellido:String,
            username:String,
            email:String,
            fechaNacimiento:DateTime,
            validado:Boolean,
            codigoValidacion:String,
            password:String
  ) = { 
    val yo = new UsuarioEntity
    yo.nombre = nombre
    yo.apellido = apellido
    yo.username = username
    yo.email = email
    yo.fechaNacimiento = fechaNacimiento
    yo.validado = validado
    yo.codigoValidacion = codigoValidacion
    yo.password = password
    yo
  }
  
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