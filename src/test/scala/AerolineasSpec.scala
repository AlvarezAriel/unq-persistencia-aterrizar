import edu.unq.persistencia.model._
import fixtures.BasicFixtureContainer
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import edu.unq.persistencia.model.Tramo._
import scala.collection.JavaConversions._

class AerolineasSpec  extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator with BasicFixtureContainer {
  val asientosHome = generateFor (classOf[Asiento])
  val tramosHome = generateFor (classOf[Tramo])
  val vuelosHome = generateFor (classOf[Vuelo])
  val aerolineasHome = generateFor (classOf[Aerolinea])

  var fixture:BasicFixture = _

  before {
    fixture = BasicFixture()
    //    asientosHome.updater.deleteAll
  }

  after {}

  "Un asiento, " should "puede ser guardado" in {
    val asiento: Asiento = Asiento(1)
    asientosHome.updater.save(asiento)
  }

  "Un Tramo, " should "ser agregado a un vuelo y guardado" in {
    tramosHome.updater.save(fixture.tramo)
    val tramoRecuperado = tramosHome.locator.get(fixture.tramo.id)
    tramoRecuperado.origen should be equals fixture.tramo.origen
  }

  "Un Vuelo, " should  "guardado con un tramo, al ser recuperado conserva sus tramos" in {
    vuelosHome.updater.save(fixture.vueloEmpty)
    fixture.tramo.vuelo = fixture.vueloEmpty
    tramosHome.updater.save(fixture.tramo)
    val vueloRecuperado = vuelosHome.locator.get(fixture.vueloEmpty.id)
    vueloRecuperado.tramos.map(_.id) should contain (fixture.tramo.id)
  }

  "Una Aerolinea, " should "conoce sus vuelos" in {


    aerolineasHome.updater.save(fixture.aerolineaLan)
    fixture.vueloEmpty.aerolinea = fixture.aerolineaLan
    vuelosHome.updater.save(fixture.vueloEmpty)

    val aerolineaRecuperada = aerolineasHome.locator.get(fixture.aerolineaLan.id)
    aerolineaRecuperada.vuelos.map(_.id) should contain (fixture.vueloEmpty.id)
  }




}
