import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.model.{Entity, Asiento}
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

class AerolineasSpec  extends FlatSpec with Matchers with BeforeAndAfter {
  val asientosHome = new HomeComponentJPA[Asiento] with DefaultSessionProviderComponent {override val clazz: Class[Asiento] = classOf[Asiento]}


  def homeGenerator[T <: Entity[_]](clase:Class[T]) = {
    new HomeComponentJPA[T] with DefaultSessionProviderComponent {override val clazz: Class[T] = clase}
  }
  before {
//    asientosHome = new HomeComponentJPA[Asiento] with DefaultSessionProviderComponent {override val clazz: Class[Asiento] = classOf[Asiento]}
  }

  after {

  }

  "Un asiento, " should "puede ser guardado" in {
    val asiento: Asiento = new Asiento(0,1,null,null)
    asientosHome.updater.save(asiento)
    asientosHome.updater.save(asiento)
    asientosHome.updater.save(asiento)
    asientosHome.updater.save(asiento)
    homeGenerator(classOf[Asiento]).updater.save(asiento)
  }
}