import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.model.login.UsuarioEntity
import edu.unq.persistencia.{model, DefaultSessionProviderComponent}
import edu.unq.persistencia.model._
import fixtures.BasicFixtureContainer
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import edu.unq.persistencia.model.Tramo._

class AsientoMappingSpec  extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator with BasicFixtureContainer {
  val asientosHome = generateFor(classOf[Asiento])
  val usuariosHome = generateFor(classOf[UsuarioEntity])
  var fixture:BasicFixture = _

  before {
    fixture = BasicFixture()
//    asientosHome.updater.deleteAll
  }

  "Un asiento, " should "puede ser guardado" in {
    val asiento = Asiento(1)
    generateFor(classOf[Asiento]).updater.save(asiento)
  }

  it should "guardado con una categoria, al ser recuperado conserva su categoria" in{
    val home = generateFor(classOf[Asiento])

    home.updater.save(fixture.asientoBusiness)
    val asientoBusinessGuardado: Asiento = home.locator.get(fixture.asientoBusiness.id)
    asientoBusinessGuardado.categoria should be equals model.Business

    home.updater.save(fixture.asientoTurista)
    val asientoTuristaGuardado: Asiento = home.locator.get(fixture.asientoTurista.id)
    asientoTuristaGuardado.categoria should be equals model.Turista

    home.updater.save(fixture.asientoPrimera)
    val asientoPrimeraGuardado: Asiento = home.locator.get(fixture.asientoPrimera.id)
    asientoPrimeraGuardado.categoria should be equals model.Primera
  }

  it should " puede ser asignado a un usuario" in{
    usuariosHome.updater.save(fixture.usuarioPepe)
    fixture.asientoBusiness.pasajero = fixture.usuarioPepe
    asientosHome.updater.save(fixture.asientoBusiness)

    val asientoRecuperado = asientosHome.locator.get(fixture.asientoBusiness.id)
//    usuariosHome.withTransaction { () =>
      asientoRecuperado.pasajero.id should be equals fixture.usuarioPepe.id
//    }


  }

}
