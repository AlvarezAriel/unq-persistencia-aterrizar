import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.model.{Tramo, Locacion, Asiento}
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import edu.unq.persistencia.model.Tramo._

class AerolineasSpec  extends FlatSpec with Matchers with BeforeAndAfter {
  val asientosHome = new HomeComponentJPA[Asiento] with DefaultSessionProviderComponent {override val clazz: Class[Asiento] = classOf[Asiento]}
  val tramosHome = new HomeComponentJPA[Tramo] with DefaultSessionProviderComponent {override val clazz: Class[Tramo] = classOf[Tramo]}

  before {
//    asientosHome = new HomeComponentJPA[Asiento] with DefaultSessionProviderComponent {override val clazz: Class[Asiento] = classOf[Asiento]}
  }

  after {

  }

  "Un asiento, " should "puede ser guardado" in {
    val asiento: Asiento = new Asiento(1,null,null)
    asientosHome.updater.save(asiento)
  }

  "Un Tramo, " should "puede ser agregado a un vuelo y guardado" in {
    val origen = new Locacion("Buenos Aires")
    val destino = new Locacion("Tokyo")
    val tramo = new Tramo(origen=origen,destino=destino,precioBase = 50)
    tramosHome.updater.save(tramo)
    val tramoRecuperado = tramosHome.locator.get(tramo.id)
  }


}
