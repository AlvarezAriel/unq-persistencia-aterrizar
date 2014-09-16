package edu.unq.persistencia.model

import edu.unq.persistencia.model.login.UsuarioEntity
import scala.beans.BeanProperty
import javax.persistence
import org.joda.time.DateTime

trait Identificable {@BeanProperty var id:Long = 0  }

class Empresa(@BeanProperty var aerolineas:Seq[Aerolinea]) extends Identificable

trait Categoria extends Identificable { val factor:BigDecimal}
object Business extends Categoria {val factor:BigDecimal = 0.3}
object Primera extends Categoria  {val factor:BigDecimal = 0.5}
object Turista extends Categoria  {val factor:BigDecimal = 0.2}

class Aerolinea( @BeanProperty var vuelos:Seq[Vuelo] ) extends Identificable {
  //  def getVuelos = vuelos
  //  def setVuelos(vuelos:Seq[Vuelo]) = this.vuelos = vuelos
}

@persistence.Entity
class Asiento(
               @BeanProperty var numero:Int,
               @BeanProperty var categoria:Categoria,
               @BeanProperty var usuario:Option[UsuarioEntity]
               )extends Entity[Asiento]

class Vuelo (
               @BeanProperty var tramos:Seq[Tramo]
) extends Identificable {
  def esDirecto: Boolean = tramos.length == 1
}

class Tramo(
             @BeanProperty var origen     :Locacion,
             @BeanProperty var destino    :Locacion,
             @BeanProperty var salida     :DateTime = DateTime.now(),
             @BeanProperty var llegada    :DateTime = DateTime.now(),
             @BeanProperty var precioBase :java.math.BigDecimal
             ) extends Entity[Asiento]

object Tramo {
  implicit def intToJavaBigDecimal(entero:Int) = java.math.BigDecimal.valueOf(entero)
}

class Locacion(@BeanProperty var nombre:String) extends Identificable
