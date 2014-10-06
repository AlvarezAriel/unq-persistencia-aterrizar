package fixtures
import edu.unq.persistencia.model.Tramo._
import edu.unq.persistencia.model._
import scala.collection.JavaConversions._
import edu.unq.persistencia.model.login.UsuarioEntity
import org.joda.time.DateTime

trait BasicFixtureContainer {
  case class BasicFixture(){
    val usuarioPepe = UsuarioEntity("Pepe", "Pompin", "sarasascosmicas", "p@p.p", DateTime.parse("2014-10-25"), validado = false, "", password = "pepon")

    val origenBuenosAires = Locacion("Buenos Aires")
    val destinoTokyo = Locacion("Tokyo")
    val tramo = Tramo(origenBuenosAires, destinoTokyo, precioBase = 50)
    val vueloEmpty = Vuelo(Set.empty[Tramo])
    val aerolineaLan = new Aerolinea
    aerolineaLan.nombre = "LAN"

    val asientoBusiness = Asiento(1, Business)
    val asientoTurista = Asiento(2, Turista)
    val asientoPrimera = Asiento(3, Primera)
  }
}
