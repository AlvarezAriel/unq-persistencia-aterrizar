import edu.unq.persistencia.cake.component.HomeComponentJPA
import edu.unq.persistencia.DefaultSessionProviderComponent
import edu.unq.persistencia.model.Entity

trait HomeCreator {
  def generateFor[T <: Entity[_]](clase:Class[T]) = {
    new HomeComponentJPA[T] {override val clazz: Class[T] = clase}
  }
}
