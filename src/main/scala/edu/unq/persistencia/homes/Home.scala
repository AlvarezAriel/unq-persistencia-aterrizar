package edu.unq.persistencia.homes

import edu.unq.persistencia.model.Entity
import java.sql.{Statement, Connection}

trait Home[T <: Entity[_]] {
  implicit val conn: Connection

  def createSchemaFor(entidad:T) = {
    val stat:Statement = conn.createStatement()
    stat.execute(entidad.createSchema)
    stat.close()
  }

  def crearNuevo(entidad:T):T = {
    val stat:Statement = conn.createStatement()
    stat.execute(s"insert into ${entidad.tableName}(${
      entidad.attributesMap.keys.mkString(",")

    }) values(${
      entidad.attributesMap.values.map( "'" + _ + "'" ).mkString(",")
    })")
    stat.close()
    entidad
  }

  def all: Seq[T] = Seq.empty[T]
}
