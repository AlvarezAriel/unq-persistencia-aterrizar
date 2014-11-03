import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.model._
import edu.unq.persistencia.model.filters.{Search, Search$, Filter}
import edu.unq.persistencia.model.login.UsuarioEntity

trait HomeCreator {
  def generateFor[T <: Entity[_]](clase:Class[T]) = {
    new HomeComponentJPA[T] {override val clazz: Class[T] = clase}
  }

  val usuariosHome = generateFor(classOf[UsuarioEntity])
  val asientosHome = generateFor(classOf[Asiento])
  val tramosHome = generateFor (classOf[Tramo])
  val vuelosHome = generateFor (classOf[Vuelo])
  val aerolineasHome = generateFor (classOf[Aerolinea])
  val searchsHome = generateFor (classOf[Search])
  val filtrosHome = generateFor (classOf[Filter])
}
