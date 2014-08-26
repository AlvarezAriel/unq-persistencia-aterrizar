import edu.unq.persistencia.homes.Home
import edu.unq.persistencia.model.UsuarioEntity
import java.sql.{ResultSet, Statement, DriverManager, Connection}
import org.h2.tools.DeleteDbFiles
import org.scalatest._


class TablesSuite extends FunSuite with BeforeAndAfter {

  implicit var conection: Connection = _
  implicit var usuariosHome: Home[UsuarioEntity]= _

  before {
    DeleteDbFiles.execute("~", "test", true)
    Class.forName("org.h2.Driver")
    conection = DriverManager.getConnection("jdbc:h2:~/test")
    class TestHome extends Home[UsuarioEntity]{
      override val conn: Connection = conection
    }
    usuariosHome = new TestHome
  }
  
  test("Creating the Schema works") {

    val stat:Statement = conection.createStatement()

    stat.execute("create table test(id int primary key, name varchar(255))")
    stat.execute("insert into test(id, nombre) values(1, 'Hello')")

    val rs:ResultSet = stat.executeQuery("select * from test")
    while(rs.next()){
      assert( rs.getString("name") === "Hello")
    }
    val usuario: UsuarioEntity = UsuarioEntity("Juan", "Perez", "dragonlady48", "j@p.com", "1985")
    println(usuario.createSchema)
    println(usuario.attributesMap)
    stat.close()
  }

  test("Locura watemalteca") {
    val usuario: UsuarioEntity = UsuarioEntity("Juan", "Perez", "dragonlady48", "j@p.com", "1985")
    val stat:Statement = conection.createStatement()
    stat.execute(usuario.createSchema)

    usuariosHome.put(usuario)

    val rs:ResultSet = stat.executeQuery(s"select * from ${usuario.tableName}")
    while(rs.next()){
      usuario.attributesMap.foreach( it =>
        assert(rs.getString(it._1) === usuario.attributesMap.get(it._1).get)
      )
    }

    stat.close()
  }

  after {
    conection.close()
  }

}