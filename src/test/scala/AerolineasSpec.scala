import edu.unq.persistencia.model._
import fixtures.BasicFixture
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import edu.unq.persistencia.model.Tramo._
import scala.collection.JavaConversions._

class AerolineasSpec  extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator with BasicFixture {
  val asientosHome = generateFor (classOf[Asiento])
  val tramosHome = generateFor (classOf[Tramo])
  val vuelosHome = generateFor (classOf[Vuelo])

  before {/*completar con reset de homes*/}

  after {}

  "Un asiento, " should "puede ser guardado" in {
    val asiento: Asiento = Asiento(1)
    asientosHome.updater.save(asiento)
  }

  "Un Tramo, " should "ser agregado a un vuelo y guardado" in {
    tramosHome.updater.save(tramo)
    val tramoRecuperado = tramosHome.locator.get(tramo.id)
    tramoRecuperado.origen should be equals tramo.origen
  }

  "Un Vuelo, " should  "guardado con un tramo, al ser recuperado conserva sus tramos" in {
    vuelosHome.updater.save(vueloEmpty)
    tramo.vuelo = vueloEmpty
    tramosHome.updater.save(tramo)
    val vueloRecuperado = vuelosHome.locator.get(vueloEmpty.id)
    vueloRecuperado.tramos.map(_.id) should contain (tramo.id)
  }




}
