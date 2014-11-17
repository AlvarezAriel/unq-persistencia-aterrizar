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
        tramosHome.updater.save(fixture.tramoBsAsTokio)
        fixture.vueloEmpty.aerolinea = fixture.aerolineaLan
        vuelosHome.updater.save(fixture.vueloEmpty)


    }

    it should "realiza búsqueda de vuelos por menor costo" in DBAction.withSession { implicit session =>
      val aSearch: Search = Select all vuelos groupBy "tramo" orderBy "SUM(tramo.precioBase)"

      aerolineasHome.updater.save(fixture.aerolineaLan)

      tramosHome.updater.save(fixture.tramoBsAsTokio)
      tramosHome.updater.save(fixture.tramoNewYorkRoma)
      tramosHome.updater.save(fixture.tramoSydneyAsuncion)

      vuelosHome.updater.save(fixture.vueloCaro)
      vuelosHome.updater.save(fixture.vueloBarato)

      searchsHome.updater.save(aSearch)

      val response = aSearch.list(session)

      response.apply(0) shouldBe fixture.vueloBarato
      response.apply(1) shouldBe fixture.vueloCaro


    }

    it should "realiza búsqueda de vuelos por menor cantidad de escalas" in DBAction.withSession { implicit session =>

      val aSearch: Search = ???

      aerolineasHome.updater.save(fixture.aerolineaLan)

      tramosHome.updater.save(fixture.tramoBsAsTokio)
      tramosHome.updater.save(fixture.tramoNewYorkRoma)
      tramosHome.updater.save(fixture.tramoSydneyAsuncion)

      vuelosHome.updater.save(fixture.vueloEmpty)
      vuelosHome.updater.save(fixture.vueloCaro)
      vuelosHome.updater.save(fixture.vueloBarato)

      val response = aSearch.list(session)

      response.length shouldBe 3
      response.apply(1) shouldBe fixture.vueloBarato
      response.apply(2) shouldBe fixture.vueloCaro
      response.apply(0) shouldBe fixture.vueloEmpty

    }

    it should "realiza una búsqueda de vuelos y la repite obteniendo otro resultado" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por aerolinea" in DBAction.withSession { implicit session =>

      val aSearch: Search = ???

      val aerolineaArg = new Aerolinea
      aerolineaArg.nombre = "ARG"

      aerolineaArg.vuelos.add(fixture.vueloEmpty)
      fixture.aerolineaLan.vuelos.add(fixture.vueloBarato)
      fixture.aerolineaLan.vuelos.add(fixture.vueloCaro)

      val response = aSearch.list(session)

      //quiero testear haciendo un includes aca, para ver que me respondió los vuelos que efectivamente
      //son de la aerolinea que le indiqué. O sea, algo del estilo response.includes(vueloEmpty)
      //en caso de haber filtrado por "ARG"



    }

    it should "realizar una búsqueda de vuelos por categoría de asiento" in DBAction.withSession { implicit session =>

      val aSearch: Search = ???

      aerolineasHome.updater.save(fixture.aerolineaLan)

      asientosHome.updater.save(fixture.asientoBusiness)
      asientosHome.updater.save(fixture.asientoTurista)
      asientosHome.updater.save(fixture.asientoPrimera)

      tramosHome.updater.save(fixture.tramoBsAsTokio)
      tramosHome.updater.save(fixture.tramoNewYorkRoma)
      tramosHome.updater.save(fixture.tramoSydneyAsuncion)

      vuelosHome.updater.save(fixture.vueloCaro)
      //AsientoBusiness
      vuelosHome.updater.save(fixture.vueloBarato)
      //AsientoTurista

      val response = aSearch.list(session)

      response.apply(0) shouldBe fixture.vueloCaro

      //Hacer otra search?

    }

    it should "realizar una búsqueda de vuelos por fecha de llegada" in DBAction.withSession { implicit session =>

      ???

    }

    it should "realizar una búsqueda de vuelos por fecha de salida" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por origen y destino" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por aerolinea y menor costo" in DBAction.withSession { implicit session => }

    it should "realizar una búsqueda de vuelos por 2 aerolineas explicitas diferentes y fecha de salida explícita" in
      DBAction.withSession { implicit session => }


}
