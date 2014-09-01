import edu.unq.persistencia.homes.Home
import edu.unq.persistencia.model.UsuarioEntity
import java.sql.{ResultSet, Statement, DriverManager, Connection}
import org.h2.tools.DeleteDbFiles
import org.scalatest._


class TablesSuite extends FunSuite with BeforeAndAfter {

  implicit var conection: Connection = _
  implicit var usuariosHome: Home[UsuarioEntity]= _
  implicit var usuario: UsuarioEntity = _

  before {
    DeleteDbFiles.execute("~", "test", true)
    Class.forName("org.h2.Driver")
    conection = DriverManager.getConnection("jdbc:h2:~/test")
    class TestHome extends Home[UsuarioEntity]{
      override val conn: Connection = conection
    }

    usuariosHome = new TestHome
    usuario = UsuarioEntity(11, "Juan", "Perez", "dragonlady48", "a@a.a", "1970-10-10", false, "", "pepita")
  }

  test("se puede crear una tabla a partir de una entidad, insertarle una y luego traerla") {

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

  after {
    conection.close()
  }

}