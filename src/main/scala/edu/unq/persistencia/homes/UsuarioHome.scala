package edu.unq.persistencia.homes

import edu.unq.persistencia.model.UsuarioEntity
import java.sql.{PreparedStatement, Statement, Connection}

trait UsuarioHome extends Home[UsuarioEntity]{
  def findUserByNameAndPassword(username: String, password: String): Option[UsuarioEntity] = {
    val stat:PreparedStatement = conn.prepareStatement(
      "SELECT * FROM UsuarioEntity WHERE username = ? and password = ?;"
    )
//    stat.setString(1, classOf[UsuarioEntity].toString)
    stat.setString(1, username)
    stat.setString(2, password)

    val resultado = UsuarioEntity.construir(stat.executeQuery())
    stat.close()

    resultado
  }

  //  override implicit val conn: Connection = _
}
