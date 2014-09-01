package edu.unq.persistencia.model
import reflect.runtime.{universe => ru}
import scala.reflect.runtime.universe._
abstract class Entity[A: TypeTag] {
  implicit val id:Int

  def fieldSymbols:Iterable[String] = typeOf[A].members.collect{ case m: ru.MethodSymbol if m.isGetter => m }.map{
    it => s"${it.name.toString} ${convertidorDeTipos(it.returnType)}"
  }.toSeq.reverse

  def createSchema:String = {
    s"CREATE TABLE $tableName( ${fieldSymbols.mkString(",")} )"
  }

  val tableName: String = this.getClass.getSimpleName

  def attributesMap = getCCParams(this)

  private def getCCParams(cc: AnyRef) =
    (Map[String, Any]() /: cc.getClass.getDeclaredFields) {(a, f) =>
      f.setAccessible(true)
      a + (f.getName -> f.get(cc))
    }

  //TODO: sacar los strings del case y usar los Type posta
  def convertidorDeTipos(algo:Type ) =
    algo.toString match  {
      case "scala.Char"    | "String"         => "VARCHAR(255)"
      case "scala.Int"     | "Int"            => "INT"
      case "scala.Boolean" | "Boolean"        => "BOOLEAN"
      case _                                  => "VARCHAR(255)"
    }

  def buildFromList(params:List[Any]) =
    this.getClass.getMethods.find(x => x.getName == "apply" && x.isBridge).get.invoke(typeOf[A], params map (_.asInstanceOf[AnyRef]): _*).asInstanceOf[A]

}