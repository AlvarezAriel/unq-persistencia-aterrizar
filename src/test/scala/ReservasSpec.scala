import edu.unq.persistencia.bussinessExceptions.AsientoYaReservado
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

  val service: ReservasService = new ReservasService

  before {
    fixture = BasicFixture()
    DBAction.withSession { implicit session =>
      usuariosHome.updater.save(fixture.usuarioPepe)
      asientosHome.updater.save(fixture.asientoBusiness)
      asientosHome.updater.save(fixture.asientoTurista)
      asientosHome.updater.save(fixture.asientoPrimera)

    }

  }

  after {}

 "Un asiento ya reservado" should "no poder ser reservado otra vez" in DBAction.withSession { implicit session =>

    service.reservarAsiento(fixture.usuarioPepe, fixture.asientoBusiness.id)
    intercept[AsientoYaReservado.type]{
      service.reservarAsiento(fixture.usuarioPepe, fixture.asientoBusiness.id)
    }
  }

  "Un asiento no reservado" should "ser reservado" in DBAction.withSession { implicit session =>

    service.reservarAsiento(fixture.usuarioPepe, fixture.asientoBusiness.id)

  }
    val asientoBusiness = Asiento(1, Business)

  "Un conjunto de asientos sin reservar" should "ser reservados" in DBAction.withSession { implicit session =>

    service.reservarAsientos(fixture.usuarioPepe, Seq(fixture.asientoBusiness.id, fixture.asientoPrimera.id, fixture.asientoTurista.id))
  }

  "Un conjunto de asientos donde uno ya está reservado" should "no se puede reservar" in DBAction.withSession { implicit session =>
    val asientos = Seq(fixture.asientoBusiness.id, fixture.asientoPrimera.id, fixture.asientoTurista.id)
    service.reservarAsiento(fixture.usuarioPepe, fixture.asientoBusiness.id)
    intercept[AsientoYaReservado.type]{
      service.reservarAsientos(fixture.usuarioPepe, asientos)
    }

  }

  "En un tramo con 3 asientos" should "devuelve un solo asiento que no está reservado"  in
    DBAction.withSession { implicit session =>
      fixture.tramo.asientos.add(fixture.asientoTurista)
      fixture.tramo.asientos.add(fixture.asientoBusiness)
      fixture.tramo.asientos.add(fixture.asientoPrimera)
      service.reservarAsientos(fixture.usuarioPepe, Seq(fixture.asientoPrimera.id, fixture.asientoTurista.id))
      service.asientosDisponiblesPara(fixture.tramo.id) should be equals Set(fixture.asientoBusiness)
  }





}
