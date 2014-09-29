import edu.unq.persistencia.model.{Tramo, Locacion, Asiento}
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import edu.unq.persistencia.model.Tramo._

class AerolineasSpec  extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator{
  val asientosHome = generateFor (classOf[Asiento])
  val tramosHome = generateFor (classOf[Tramo])

  before {/*completar con reset de homes*/}

  after {}

  "Un asiento, " should "puede ser guardado" in {
    val asiento: Asiento = Asiento(1)
    asientosHome.updater.save(asiento)
  }

  "Un Tramo, " should "ser agregado a un vuelo y guardado" in {
    val origen = Locacion("Buenos Aires")
    val destino = Locacion("Tokyo")
    val tramo = Tramo(origen, destino, precioBase = 50)
    tramosHome.updater.save(tramo)
    val tramoRecuperado = tramosHome.locator.get(tramo.id)
    tramoRecuperado.origen should be equals tramo.origen
  }

  it should "poder tener asientos" in {
    val origen = Locacion("Buenos Aires")
    val destino = Locacion("Tokyo")
    val tramo = Tramo(origen, destino, precioBase = 50)
    tramo.asientos.add()
    tramosHome.updater.save(tramo)
    val tramoRecuperado = tramosHome.locator.get(tramo.id)




    tramoRecuperado.origen should be equals tramo.origen
  }




}
