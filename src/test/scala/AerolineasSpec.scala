import edu.unq.persistencia.cake.component.{HomeComponent, HomeComponentJPA}
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.model.empresa.Asiento
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

class AerolineasSpec  extends FlatSpec with Matchers with BeforeAndAfter {
  val asientosHome = new HomeComponentJPA[Asiento] with DefaultSessionProviderComponent {override val clazz: Class[Asiento] = classOf[Asiento]}

  before {
//    asientosHome = new HomeComponentJPA[Asiento] with DefaultSessionProviderComponent {override val clazz: Class[Asiento] = classOf[Asiento]}
  }

  after {

  }

  "Encuesta Service, para un año dado, " should "saber la cantidad de ventas totales" in {
    val asiento: Asiento = new Asiento(1,null,null)
    asientosHome.updater.save(asiento)
  }
}
