import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.cake.component.DBAction
import edu.unq.persistencia.services.AmigosService
import fixtures.BasicFixtureContainer
import org.anormcypher.Neo4jREST
import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

class AnormCypher extends FlatSpec with Matchers with BeforeAndAfter with HomeCreator with BasicFixtureContainer with DefaultSessionProviderComponent{
    implicit val connection = Neo4jREST
    import org.anormcypher._

    val amigosService = new AmigosService
    var fixture: BasicFixture = _

    before {
        fixture = BasicFixture()
        DBAction.withSession { implicit session =>
            usuariosHome.updater.save(fixture.usuarioPepe)
            usuariosHome.updater.save(fixture.usuarioMaria)
            usuariosHome.updater.save(fixture.usuarioCarlono)
            usuariosHome.updater.save(fixture.usuarioRonny)
            usuariosHome.updater.save(fixture.usuarioNahue)
        }
    }

    after {
        implicit val connection = Neo4jREST
        Cypher("""START n=node(*) OPTIONAL MATCH (n)-[r]-() delete n,r;""").execute()
    }

    "Un usuario" should "poder amigarse con otro" in {
        amigosService.amigarseCon(fixture.usuarioMaria, fixture.usuarioCarlono)

        val req = Cypher("MATCH (user { name:'Maria' })-->(target {name:'Carlono'}) RETURN user.name, target.name")
        val nombres = req().head
        fixture.usuarioMaria.nombre should be equals nombres[String]("user.name")
        fixture.usuarioCarlono.nombre should be equals nombres[String]("target.name")
    }

    "Un usuario" should "poder conocer a sus amigos" in {
        amigosService.amigarseCon(fixture.usuarioMaria, fixture.usuarioCarlono)
        amigosService.amigarseCon(fixture.usuarioMaria, fixture.usuarioPepe)

        val amigos: List[Int] = amigosService.misAmigos(fixture.usuarioMaria)

        amigos.size shouldBe 2
        amigos.contains(fixture.usuarioCarlono.id) shouldBe true
        amigos.contains(fixture.usuarioPepe.id)  shouldBe true
    }

}
