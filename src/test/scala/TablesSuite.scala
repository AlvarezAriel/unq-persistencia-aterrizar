import edu.unq.persistencia.model.UsuarioEntity
import java.sql.{ResultSet, Statement, DriverManager, Connection}
import org.h2.tools.DeleteDbFiles
import org.scalatest._
import scala.slick.driver.H2Driver.simple._
import scala.slick.jdbc.meta._
import scala.slick.jdbc.StaticQuery.interpolation


class TablesSuite extends FunSuite with BeforeAndAfter {

  implicit var conn: Connection = _

  before {
    DeleteDbFiles.execute("~", "test", true)
    Class.forName("org.h2.Driver")
    conn = DriverManager.getConnection("jdbc:h2:~/test")
  }
  
  test("Creating the Schema works") {

    val stat:Statement = conn.createStatement()

    stat.execute("create table test(id int primary key, name varchar(255))")
    stat.execute("insert into test values(1, 'Hello')")

    val rs:ResultSet = stat.executeQuery("select * from test")
    while(rs.next()){
      assert( rs.getString("name") === "Hello")
    }
    println(UsuarioEntity("Juan","Perez","dragonlady48","j@p.com","1985").createSchema)
    stat.close()
  }


  after {
    conn.close()
  }

}