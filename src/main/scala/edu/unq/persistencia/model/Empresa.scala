package edu.unq.persistencia.model

import edu.unq.persistencia.model.login.UsuarioEntity
import scala.beans.BeanProperty
import scala.collection._
import org.joda.time.DateTime
import scala.collection.JavaConversions._

trait Identificable {@BeanProperty var id:Long = 0  }

class Empresa(@BeanProperty var aerolineas:java.util.Set[Aerolinea]) extends Identificable

trait Categoria extends Identificable with Serializable { val factor:BigDecimal}
object Business extends Categoria {val factor:BigDecimal = 0.3}
object Primera extends Categoria  {val factor:BigDecimal = 0.5}
object Turista extends Categoria  {val factor:BigDecimal = 0.2}

class Aerolinea extends Identificable {
  @BeanProperty var vuelos:java.util.Set[Vuelo] = _
  //  def getVuelos = vuelos
  //  def setVuelos(vuelos:Seq[Vuelo]) = this.vuelos = vuelos
}

class Asiento extends Entity[Asiento] {
   @BeanProperty var numero:   Int = _
   @BeanProperty var categoria:Categoria = Turista
   @BeanProperty var pasajero: UsuarioEntity = _
   @BeanProperty var tramo:    Tramo = _

}

class Vuelo extends Entity[Vuelo] with Identificable  {
  @BeanProperty var tramos:java.util.Set[Tramo] = Set.empty[Tramo]
  def esDirecto: Boolean = tramos.size() == 1
}

class Tramo extends Entity[Tramo] {
  @BeanProperty var origen     :Locacion = _
  @BeanProperty var destino    :Locacion = _
  @BeanProperty var salida     :DateTime = DateTime.now()
  @BeanProperty var llegada    :DateTime = DateTime.now()
  @BeanProperty var precioBase :java.math.BigDecimal = _
  @BeanProperty var asientos   :java.util.Set[Asiento] = Set.empty[Asiento]
  @BeanProperty var vuelo   :Vuelo = _


}

class Locacion extends Entity[Locacion] with Identificable {@BeanProperty var nombre:String = ""}

/***************************************************************************************/
/******************************* COMPANION OBJECTS *************************************/

object Vuelo { def apply(tramos:java.util.Set[Tramo]) = {val esto = new Vuelo();esto.tramos=tramos;esto}}


object Tramo {
  implicit def intToJavaBigDecimal(entero:Int) = java.math.BigDecimal.valueOf(entero)
  def apply(origen:Locacion, destino:Locacion, precioBase:java.math.BigDecimal) = {
    val esto = new Tramo(); esto.origen = origen; esto.precioBase = precioBase; esto
  }
}

object Asiento { def apply(numero: Int) = {val esto = new Asiento;esto.numero = numero;esto;}
  def apply(numero:Int, categoria: Categoria) : Asiento = { val esto = apply(numero);esto.categoria = categoria;esto }
}
object Locacion { def apply(nombre:String) = {val esto = new Locacion;esto.nombre=nombre;esto}}
