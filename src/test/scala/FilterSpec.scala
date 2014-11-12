import com.sun.xml.internal.bind.v2.model.core.ID
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.cake.component.DBAction
import edu.unq.persistencia.model.Vuelo
import edu.unq.persistencia.model.filters._
import edu.unq.persistencia.model.filters.VuelosValueContainer._
import edu.unq.persistencia.model.filters.Filter.SymbolsToExpressions
import edu.unq.persistencia.services.ReservasService
import fixtures.BasicFixtureContainer
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

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
        val aSearch: Search = Select all vuelos where "aerolinea.id" =? 1 || "aerolinea.id" =? 0
        aerolineasHome.updater.save(fixture.aerolineaLan)
        vuelosHome.updater.save(fixture.vueloEmpty)
        searchsHome.updater.save(aSearch)

        val idsVuelos = aSearch.list
        val sarasa = 1
    }

    it should "poder realizar una búsqueda" in DBAction.withSession { implicit session =>
        tramosHome.updater.save(fixture.tramo)
        fixture.vueloEmpty.aerolinea = fixture.aerolineaLan
        vuelosHome.updater.save(fixture.vueloEmpty)


    }

    it should "realiza búsqueda de vuelos por menor costo" in DBAction.withSession { implicit session => }

    it should "realiza búsqueda de vuelos por menor cantidad de escalas" in DBAction.withSession { implicit session => }

    it should "realiza una búsqueda de vuelos y la repite obteniendo otro resultado" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por aerolinea" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por categoría de asiento" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por fecha de llegada" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por fecha de salida" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por origen y destino" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por aerolinea y menor costo" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por 2 aerolineas explicitas diferentes y fecha de salida explícita" in
      DBAction.withSession { implicit session => }


}
