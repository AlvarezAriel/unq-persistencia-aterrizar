import edu.unq.persistencia.cake.component.DBAction
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.model._
import edu.unq.persistencia.model.login.UsuarioEntity
import edu.unq.persistencia.services.ReservasService
import fixtures.BasicFixtureContainer
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}
import scala.collection.JavaConversions._

class ReservasSpec  extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator with BasicFixtureContainer with DefaultSessionProviderComponent{

  var fixture:BasicFixture = _

  before {
    fixture = BasicFixture()
    DBAction.withSession { implicit session =>
      usuariosHome.updater.save(fixture.usuarioPepe)
      asientosHome.updater.save(fixture.asientoBusiness)
    }

  }

  after {}

  "ReservasService" should "reservar un asiento libre" in DBAction.withSession { implicit session =>
    val service: ReservasService = new ReservasService
    service.reservarAsiento(fixture.usuarioPepe, fixture.asientoBusiness)

  }

}
