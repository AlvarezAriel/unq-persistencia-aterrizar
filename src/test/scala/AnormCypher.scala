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

    "Un usuario" should "poder enviarle un mensaje a otro" in {
        amigosService.amigarseCon(fixture.usuarioMaria, fixture.usuarioCarlono)
        val hola: String = "hola"
        amigosService.enviarMensaje(fixture.usuarioMaria, fixture.usuarioCarlono, hola)
        val contenido = Cypher("MATCH (user { name:'Maria'})-[:ENVIO]-> (mensaje) RETURN mensaje.contenido")().head[String]("mensaje.contenido")
        contenido should be equals hola
    }

    "Un usuario" should "poder conocer a todos sus contactos" in {
        amigosService.amigarseCon(fixture.usuarioMaria, fixture.usuarioCarlono)
        //pepe es amigo de carlono pero no a la inversa, no debería aparecer listado
        amigosService.amigarseCon(fixture.usuarioPepe, fixture.usuarioCarlono)

        amigosService.amigarseCon(fixture.usuarioCarlono, fixture.usuarioNahue)
        amigosService.amigarseCon(fixture.usuarioNahue, fixture.usuarioRonny)
        //acá hay un bucle, carlono debería aparecer una única vez
        amigosService.amigarseCon(fixture.usuarioNahue, fixture.usuarioCarlono)

        val contactos = amigosService.misContactos(fixture.usuarioMaria)
        contactos.size should be equals 3
        contactos.contains(fixture.usuarioPepe.id)  shouldBe false
        contactos.contains(fixture.usuarioCarlono.id) shouldBe true
        contactos.contains(fixture.usuarioNahue.id) shouldBe true
        contactos.contains(fixture.usuarioRonny.id) shouldBe true
    }

}
