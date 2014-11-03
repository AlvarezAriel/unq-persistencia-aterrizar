import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.model._
import edu.unq.persistencia.model.filters.Filter
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
  val filtrosHome = generateFor (classOf[Filter])
}
