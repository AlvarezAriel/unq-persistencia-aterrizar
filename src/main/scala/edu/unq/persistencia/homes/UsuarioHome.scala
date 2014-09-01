package edu.unq.persistencia.homes

import edu.unq.persistencia.model.UsuarioEntity
import java.sql.{SQLException, PreparedStatement, Statement, Connection}
import java.math.BigInteger
import java.security.SecureRandom

trait UsuarioHome extends Home[UsuarioEntity]{

  def changePassword(entity: UsuarioEntity, nuevaPassword:String) = {
    val stat:PreparedStatement = conn.prepareStatement(
      "UPDATE UsuarioEntity SET password = ? WHERE username = ?" //TODO: generalizar el nombre de la tabla
    )
    stat.setString(1, nuevaPassword)
    stat.setString(2, entity.username)
    stat.executeUpdate()
    stat.close()
  }

  def findUserByNameAndPassword(username: String, password: String): Option[UsuarioEntity] = {
    val stat:PreparedStatement = conn.prepareStatement(
      "SELECT * FROM UsuarioEntity WHERE username = ? and password = ?;" //TODO: generalizar el nombre de la tabla
    )
    stat.setString(1, username)
    stat.setString(2, password)

    val resultado = UsuarioEntity.construir(stat.executeQuery())
    stat.close()

    resultado
  }

  def findUserByUsername(username: String): Option[UsuarioEntity] = {
    val stat:PreparedStatement = conn.prepareStatement(
      "SELECT * FROM UsuarioEntity WHERE username = ?" //TODO: generalizar el nombre de la tabla
    )
    stat.setString(1, username)

    val resultado = UsuarioEntity.construir(stat.executeQuery())
    stat.close()

    resultado
  }

  def findUserByCodigoDeValidacion(codigoValidacion: String): Option[UsuarioEntity] = {
    val stat:PreparedStatement = conn.prepareStatement(
      "SELECT * FROM UsuarioEntity WHERE codigoValidacion = ?" //TODO: generalizar el nombre de la tabla
    )
    stat.setString(1, codigoValidacion)

    val resultado = UsuarioEntity.construir(stat.executeQuery())
    stat.close()

    resultado
  }

  def marcarComoValidado(entity: UsuarioEntity) = {
    val stat:PreparedStatement = conn.prepareStatement(
      "UPDATE UsuarioEntity SET validado = True WHERE username = ?" //TODO: generalizar el nombre de la tabla
    )
    stat.setString(1, entity.username)
    stat.executeUpdate()
    stat.close()
  }

  override def crearNuevo(entidad:UsuarioEntity):UsuarioEntity = {
    super.crearNuevo(entidad)
    val stat:PreparedStatement = conn.prepareStatement(
      "UPDATE UsuarioEntity SET codigoValidacion = ? WHERE username = ?" //TODO: generalizar el nombre de la tabla
    )

    val hash = new BigInteger(130, new SecureRandom()).toString(32)

    stat.setString(1, hash)
    stat.setString(2, entidad.username)
    stat.executeUpdate()
    stat.close()

    findUserByUsername(entidad.username) match {
      case Some(u) => u
    }
  }

  //  override implicit val conn: Connection = _
}
