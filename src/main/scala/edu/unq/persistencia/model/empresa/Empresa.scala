package edu.unq.persistencia.model.empresa

import org.joda.time.DateTime
import edu.unq.persistencia.model.login.UsuarioEntity

class Empresa(var aerolineas:Seq[Aerolinea])
class Aerolinea(var vuelos:Seq[Vuelo])

class Vuelo(
   var tramos:Seq[Tramo]
){
  def esDirecto: Boolean = tramos.length == 1
}

class Tramo(
  var origen:Locacion,
  var destino:Locacion,
  var salida:DateTime,
  var llegada:DateTime,
  var precioBase:BigDecimal
)

class Asiento(
  var numero:Int,
  var categoria:Categoria,
  var usuario:Option[UsuarioEntity]
)

trait Categoria { val factor:BigDecimal}
object Primera extends Categoria  {val factor:BigDecimal = 0.5}
object Business extends Categoria {val factor:BigDecimal = 0.3}
object Turista extends Categoria  {val factor:BigDecimal = 0.2}

class Locacion(nombre:String)
