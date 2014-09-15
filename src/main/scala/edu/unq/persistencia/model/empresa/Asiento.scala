package edu.unq.persistencia.model.empresa

import edu.unq.persistencia.model.login.UsuarioEntity
import scala.beans.BeanProperty
import edu.unq.persistencia.model.Entity

class Asiento(
               @BeanProperty var numero:Int,
               @BeanProperty var categoria:Categoria,
               @BeanProperty var usuario:Option[UsuarioEntity]
)extends Entity[Asiento] with Identificable
