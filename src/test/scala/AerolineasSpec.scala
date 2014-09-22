import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.model.{Entity,Tramo, Locacion, Asiento}
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import edu.unq.persistencia.model.Tramo._

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
    val asiento: Asiento = new Asiento(1,null,null)
    homeGenerator(classOf[Asiento]).updater.save(asiento)
  }

  "Un Tramo, " should "puede ser agregado a un vuelo y guardado" in {
    val origen = new Locacion("Buenos Aires")
    val destino = new Locacion("Tokyo")
    val tramo = new Tramo(origen=origen,destino=destino,precioBase = 50)
    homeGenerator(classOf[Tramo]).updater.save(tramo)
    val tramoRecuperado = homeGenerator(classOf[Tramo]).locator.get(tramo.id)
    tramoRecuperado.origen should be equals tramo.origen
  }


}
