import edu.unq.persistencia.cake.component.DBAction
import edu.unq.persistencia.{model, DefaultSessionProviderComponent}
import edu.unq.persistencia.model._
import fixtures.BasicFixtureContainer
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

class AsientoMappingSpec  extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator with BasicFixtureContainer with DefaultSessionProviderComponent {
  var fixture:BasicFixture = _

  before {
    fixture = BasicFixture()
    DBAction.withSession { implicit session =>
      usuariosHome.updater.save(fixture.usuarioPepe)
    }
  }

  "Un asiento, " should "puede ser guardado" in DBAction.withSession { implicit session =>
    val asiento = Asiento(1)
    generateFor(classOf[Asiento]).updater.save(asiento)
  }

  it should "guardado con una categoria, al ser recuperado conserva su categoria" in DBAction.withSession { implicit session =>
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

  it should " puede ser asignado a un usuario" in DBAction.withSession { implicit session =>
    fixture.asientoBusiness.pasajero = fixture.usuarioPepe
    asientosHome.updater.save(fixture.asientoBusiness)

    val asientoRecuperado = asientosHome.locator.get(fixture.asientoBusiness.id)
    asientoRecuperado.pasajero.id should be equals fixture.usuarioPepe.id

  }

}
