package edu.unq.persistencia.mailing

import edu.unq.persistencia.model.UsuarioEntity

trait EnviadorDeMails {
  def enviarMail(mail:Mail)
}

case class Mail(body:String, subject:String, to:String, from:String)

object CodigoValidacionEmail {
  def buildBody(usuario:UsuarioEntity) =
    s"""
      | Hola ${usuario.nombre} ${usuario.apellido},
      | Ingrese el siguiente código para validar su cuenta:
      | ${usuario.codigoValidacion}
    """.stripMargin
}

class EnviadorDeMailsMock extends EnviadorDeMails {
  var mailEnviado: Mail = _

  override def enviarMail(mail: Mail): Unit =
    mailEnviado = mail
}