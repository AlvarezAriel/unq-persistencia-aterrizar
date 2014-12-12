import com.sun.xml.internal.bind.v2.model.core.ID
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.cake.component.DBAction
import edu.unq.persistencia.model.{Aerolinea, Vuelo}
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

            aerolineasHome.updater.save(fixture.aerolineaLan)

            fixture.vueloCaro.setAerolinea(fixture.aerolineaLan)
            vuelosHome.updater.save(fixture.vueloCaro)

            locacionHome.updater.save(fixture.origenBuenosAires)
            locacionHome.updater.save(fixture.destinoTokyo)

            fixture.tramoBsAsTokio.origen = fixture.origenBuenosAires
            fixture.tramoBsAsTokio.destino = fixture.destinoTokyo
            fixture.tramoBsAsTokio.vuelo = fixture.vueloCaro
            tramosHome.updater.save(fixture.tramoBsAsTokio)

            fixture.asientoBusiness.tramo = fixture.tramoBsAsTokio
            asientosHome.updater.save(fixture.asientoBusiness)

          ///////////////////////////////////////

            aerolineasHome.updater.save(fixture.aerolineaLan)

            fixture.vueloBarato.setAerolinea(fixture.aerolineaLan)
            vuelosHome.updater.save(fixture.vueloBarato)

            locacionHome.updater.save(fixture.origenSydney)
            locacionHome.updater.save(fixture.destinoAsuncion)

            fixture.tramoSydneyAsuncion.origen = fixture.origenSydney
            fixture.tramoSydneyAsuncion.destino = fixture.destinoAsuncion
            fixture.tramoSydneyAsuncion.vuelo = fixture.vueloBarato
            tramosHome.updater.save(fixture.tramoBsAsTokio)

            fixture.asientoTurista.tramo = fixture.tramoSydneyAsuncion
            asientosHome.updater.save(fixture.asientoTurista)

        }
    }

    after {}

    "Un search" should "ser guadrado y recuperado" in DBAction.withSession { implicit session =>
        val aSearch: Search = Select all vuelos where "aerolinea.id" =? 1 || "aerolinea.id" =? 0
        searchsHome.updater.save(aSearch)
        val savedSearch: Search = searchsHome.locator.get(aSearch.id)
        savedSearch.filter.asInstanceOf[OR].filters.size() shouldBe 2
    }

    it should "poder realizar una búsqueda" in DBAction.withSession { implicit session =>
        val aSearch: Search = Select all vuelos
        searchsHome.updater.save(aSearch)
        val response = aSearch.list()
        response.size shouldBe 2
    }

    it should "realiza búsqueda de vuelos por menor costo" in DBAction.withSession { implicit session =>
        val aSearch: Search = Select all vuelos groupBy "tramo" orderBy "SUM(tramo.precioBase)"

        searchsHome.updater.save(aSearch)

        val response = aSearch.list()

        response(0) shouldBe fixture.vueloBarato.id
        response(1) shouldBe fixture.vueloCaro.id

    }

    it should "realiza búsqueda de vuelos por menor cantidad de escalas" in DBAction.withSession { implicit session =>

        val aSearch: Search = Select all vuelos groupBy "tramo" orderBy "COUNT(tramo.id)"

        val response = aSearch.list()

        response(0) shouldBe fixture.vueloCaro.id

    }

    it should "realiza una búsqueda de vuelos y la repite obteniendo otro resultado" in DBAction.withSession { implicit session =>}

    it should "realizar una búsqueda de vuelos por aerolinea" in DBAction.withSession { implicit session =>

//        val aSearch: Search = ???
//
//        val aerolineaArg = new Aerolinea
//        aerolineaArg.nombre = "ARG"
//
//        aerolineaArg.vuelos.add(fixture.vueloEmpty)
//        fixture.aerolineaLan.vuelos.add(fixture.vueloBarato)
//        fixture.aerolineaLan.vuelos.add(fixture.vueloCaro)
//
//        val response = aSearch.list()

        //quiero testear haciendo un includes aca, para ver que me respondió los vuelos que efectivamente
        //son de la aerolinea que le indiqué. O sea, algo del estilo response.includes(vueloEmpty)
        //en caso de haber filtrado por "ARG"


    }

    it should "realizar una búsqueda de vuelos por categoría de asiento" in DBAction.withSession { implicit session =>
//
//        val aSearch: Search = ???
//
//        aerolineasHome.updater.save(fixture.aerolineaLan)
//
//        asientosHome.updater.save(fixture.asientoBusiness)
//        asientosHome.updater.save(fixture.asientoTurista)
//        asientosHome.updater.save(fixture.asientoPrimera)
//
//        tramosHome.updater.save(fixture.tramoBsAsTokio)
//        tramosHome.updater.save(fixture.tramoNewYorkRoma)
//        tramosHome.updater.save(fixture.tramoSydneyAsuncion)
//
//        vuelosHome.updater.save(fixture.vueloCaro)
//        //AsientoBusiness
//        vuelosHome.updater.save(fixture.vueloBarato)
//        //AsientoTurista
//
//        val response = aSearch.list()
//
//        response.apply(0) shouldBe fixture.vueloCaro

        //Hacer otra search?

    }

    it should "realizar una búsqueda de vuelos por fecha de llegada" in DBAction.withSession { implicit session =>

//        ???

    }

    it should "realizar una búsqueda de vuelos por fecha de salida" in DBAction.withSession { implicit session =>}

    it should "realizar una búsqueda de vuelos por origen y destino" in DBAction.withSession { implicit session =>}

    it should "realizar una búsqueda de vuelos por aerolinea y menor costo" in DBAction.withSession { implicit session =>}

    it should "realizar una búsqueda de vuelos por 2 aerolineas explicitas diferentes y fecha de salida explícita" in
        DBAction.withSession { implicit session =>}


}
