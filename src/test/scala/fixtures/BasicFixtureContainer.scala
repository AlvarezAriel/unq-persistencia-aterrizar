package fixtures
import edu.unq.persistencia.model.Tramo._
import edu.unq.persistencia.model._
import scala.collection.JavaConversions._
import edu.unq.persistencia.model.login.UsuarioEntity
import org.joda.time.DateTime
import scala.collection._

trait BasicFixtureContainer {
  case class BasicFixture(){
    val usuarioPepe = UsuarioEntity("Pepe", "Pompin", "sarasascosmicas", "p@p.p", DateTime.parse("2014-10-25"), validado = false, "", password = "pepon")

    val origenBuenosAires = Locacion("Buenos Aires")
    val origenNewYork = Locacion("New York")
    val origenSydney = Locacion("Sydney")
    val destinoTokyo = Locacion("Tokyo")
    val destinoRoma = Locacion("Roma")
    val destinoAsuncion = Locacion("Asuncion")

    val tramoBsAsTokio = Tramo(origenBuenosAires, destinoTokyo, precioBase = 5000)
    val tramoNewYorkRoma = Tramo(origenNewYork, destinoRoma, precioBase = 8000)
    val tramoSydneyAsuncion = Tramo(origenSydney, destinoAsuncion, precioBase = 4000)

    val vueloEmpty = Vuelo(mutable.Set.empty[Tramo])
    // 0 escalas
    val vueloCaro = Vuelo(mutable.Set(tramoBsAsTokio, tramoNewYorkRoma, tramoSydneyAsuncion))
    // 3 escalas
    val vueloBarato = Vuelo(mutable.Set(tramoSydneyAsuncion))
    // 1 escala?

    val aerolineaLan = new Aerolinea
    aerolineaLan.nombre = "LAN"

    val asientoBusiness = Asiento(1, Business)
    val asientoTurista = Asiento(2, Turista)
    val asientoPrimera = Asiento(3, Primera)


  }
}
