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

class Aerolinea extends Identificable {
  @BeanProperty var vuelos:Seq[Vuelo] = _
  //  def getVuelos = vuelos
  //  def setVuelos(vuelos:Seq[Vuelo]) = this.vuelos = vuelos
}

class Asiento extends Entity[Asiento] {
   @BeanProperty var numero:Int = _
   @BeanProperty var categoria:Categoria = _
   @BeanProperty var usuario:Option[UsuarioEntity] = _
}
object Asiento { def apply(numero: Int) = {val esto = new Asiento;esto.numero = numero;esto;}}

class Vuelo extends Identificable {
  @BeanProperty var tramos:Seq[Tramo] = Seq.empty[Tramo]
  def esDirecto: Boolean = tramos.length == 1
}
//user type
class Tramo extends Entity[Asiento] {
  @BeanProperty var origen     :Locacion = _
  @BeanProperty var destino    :Locacion = _
  @BeanProperty var salida     :DateTime = DateTime.now()
  @BeanProperty var llegada    :DateTime = DateTime.now()
  @BeanProperty var precioBase :java.math.BigDecimal = _

}

class Locacion extends Identificable {@BeanProperty var nombre:String = ""}



/******************************* COMPANION OBJECTS *************************************/

object Vuelo { def apply(tramos:Seq[Tramo]) = {val esto = new Vuelo();esto.tramos=tramos;esto}}

object Tramo {
  implicit def intToJavaBigDecimal(entero:Int) = java.math.BigDecimal.valueOf(entero)
  def apply(origen:Locacion, destino:Locacion, precioBase:java.math.BigDecimal) = {
    val esto = new Tramo(); esto.origen = origen; esto.precioBase = precioBase; esto
  }
}
object Locacion { def apply(nombre:String) = {val esto = new Locacion;esto.nombre=nombre;esto}}
