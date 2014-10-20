import edu.unq.persistencia.cake.component.DBAction
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.model._
import edu.unq.persistencia.services._
import fixtures.BasicFixtureContainer
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import scala.collection.JavaConversions._

class AerolineasSpec extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator with BasicFixtureContainer with DefaultSessionProviderComponent{

  var fixture:BasicFixture = _

  before {
    fixture = BasicFixture()
  }

  after {}

  "Un asiento, " should "puede ser guardado" in DBAction.withSession { implicit session =>
    val asiento: Asiento = Asiento(1)
    asientosHome.updater.save(asiento)
  }

  "Un Tramo, " should "ser agregado a un vuelo y guardado" in DBAction.withSession { implicit session =>
    tramosHome.updater.save(fixture.tramo)
    val tramoRecuperado = tramosHome.locator.get(fixture.tramo.id)
    tramoRecuperado.origen should be equals fixture.tramo.origen
  }

  "Un Vuelo, " should  "guardado con un tramo, al ser recuperado conserva sus tramos" in DBAction.withSession { implicit session =>

    fixture.vueloEmpty.tramos.add(fixture.tramo)
    vuelosHome.updater.save(fixture.vueloEmpty)
    val vueloRecuperado = vuelosHome.locator.get(fixture.vueloEmpty.id)
    vueloRecuperado.tramos.map(_.id) should contain (fixture.tramo.id)
  }

  "Una Aerolinea, " should "conoce sus vuelos" in DBAction.withSession { implicit session =>

    fixture.aerolineaLan.vuelos.add(fixture.vueloEmpty)
    aerolineasHome.updater.save(fixture.aerolineaLan)

    val aerolineaRecuperada = aerolineasHome.locator.get(fixture.aerolineaLan.id)
    aerolineaRecuperada.vuelos.map(_.id) should contain (fixture.vueloEmpty.id)
  }




}
