package fixtures
import edu.unq.persistencia.model.Tramo._
import edu.unq.persistencia.model.{Vuelo, Tramo, Locacion}
import scala.collection.JavaConversions._

trait BasicFixture {
  val origenBuenosAires = Locacion("Buenos Aires")
  val destinoTokyo = Locacion("Tokyo")
  val tramo = Tramo(origenBuenosAires, destinoTokyo, precioBase = 50)
  val vueloEmpty = Vuelo(Set.empty[Tramo])
}
