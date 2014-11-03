import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.bussinessExceptions.AsientoYaReservado
import edu.unq.persistencia.cake.component.DBAction
import edu.unq.persistencia.model.filters._
import edu.unq.persistencia.model.filters.Filter._
import edu.unq.persistencia.services.ReservasService
import fixtures.BasicFixtureContainer
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import scala.collection.JavaConversions._

class FilterSpec extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator with BasicFixtureContainer with DefaultSessionProviderComponent {

    var fixture: BasicFixture = _

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

    "Un search" should "ser guadrado y recuperado" in DBAction.withSession { implicit session =>
        val aSearch: Search =
            Search(
                (('aerolinea, ID) =? 1 && ('method, DATE) =? "2014-05-11") || ('aerolinea, ID) =? 2
            ) orderBy 'nombre
        searchsHome.updater.save(aSearch)

        val fetchedSearch = searchsHome.locator.get(aSearch.id)
        fetchedSearch.order.propertyName shouldBe  "nombre"
    }

    

    it should "realiza búsqueda de vuelos por menor costo" in DBAction.withSession { implicit session => }

    it should "realiza búsqueda de vuelos por menor cantidad de escalas" in DBAction.withSession { implicit session => }

    it should "realiza una búsqueda de vuelos y la repite obteniendo otro resultado" in DBAction.withSession { implicit session => }

    it should "realiza una búsqueda de vuelos por menor duración" in DBAction.withSession { implicit session => }

    "" should "" in DBAction.withSession { implicit session => }


}
