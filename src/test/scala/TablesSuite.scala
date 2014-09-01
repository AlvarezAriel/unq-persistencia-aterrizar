import edu.unq.persistencia.homes.{UsuarioHome, Home}
import edu.unq.persistencia.model.UsuarioEntity
import edu.unq.persistencia.services.UsuarioService
import java.sql.{ResultSet, Statement, DriverManager, Connection}
import org.h2.tools.DeleteDbFiles
import org.scalatest._


class TablesSuite extends FunSuite with BeforeAndAfter {

  implicit var conection: Connection = _
  implicit var usuariosHome: UsuarioHome =  _
  implicit var usuario: UsuarioEntity = _
  implicit var usuarioService: UsuarioService = _

  before {
    DeleteDbFiles.execute("~", "test", true)
    Class.forName("org.h2.Driver")
    conection = DriverManager.getConnection("jdbc:h2:~/test")
    class UsuarioTestHome extends UsuarioHome { override val conn: Connection = conection }

    usuariosHome = new UsuarioTestHome
    usuario = UsuarioEntity(11, "Juan", "Perez", "dragonlady48", "a@a.a", "1970-10-10", false, "", "pepita")

    usuarioService = new UsuarioService {
      override implicit val usuarioHome: UsuarioHome = usuariosHome
    }
  }

  test("Se puede guardar un usuario nuevo") {

    usuariosHome.createSchemaFor(usuario)
    usuariosHome.put(usuario)

    val stat:Statement = conection.createStatement()
    val rs:ResultSet = stat.executeQuery(s"select * from ${usuario.tableName}")
    while(rs.next()){
      usuario.attributesMap.foreach( it =>
        assert(rs.getObject(it._1) === usuario.attributesMap.get(it._1).get)
      )
    }

    stat.close()
  }

  test("Se puede ingresar un usuario") {
    usuariosHome.createSchemaFor(usuario)
    usuariosHome.put(usuario)
    assert(usuarioService.ingresarUsuario("dragonlady48", "pepita") !== None)
  }

  after {
    conection.close()
  }

}