import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.{model, DefaultSessionProviderComponent}
import edu.unq.persistencia.model._
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import edu.unq.persistencia.model.Tramo._

class AsientoMappingSpec  extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator {
  val asientosHome = new HomeComponentJPA[Asiento] with DefaultSessionProviderComponent {override val clazz: Class[Asiento] = classOf[Asiento]}

  "Un asiento, " should "puede ser guardado" in {
    val asiento = Asiento(1)
    generateFor(classOf[Asiento]).updater.save(asiento)
  }

  it should "guardado con una categoria, al ser recuperado conserva su categoria" in{
    val asientoBusiness = Asiento(1, Business)
    val asientoTurista = Asiento(2, Turista)
    val asientoPrimera = Asiento(3, Primera)

    val home = generateFor(classOf[Asiento])

    home.updater.save(asientoBusiness)
    val asientoBusinessGuardado: Asiento = home.locator.get(asientoBusiness.id)
    asientoBusinessGuardado.categoria should be equals model.Business

    home.updater.save(asientoTurista)
    val asientoTuristaGuardado: Asiento = home.locator.get(asientoTurista.id)
    asientoTuristaGuardado.categoria should be equals model.Turista

    home.updater.save(asientoPrimera)
    val asientoPrimeraGuardado: Asiento = home.locator.get(asientoPrimera.id)
    asientoPrimeraGuardado.categoria should be equals model.Primera



  }


}
