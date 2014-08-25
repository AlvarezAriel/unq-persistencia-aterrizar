package edu.unq.persistencia.model
import reflect.runtime.{universe => ru}
import scala.reflect.runtime.universe._
abstract class Entity[A: TypeTag] {

  def fieldSymbols:Iterable[String] = typeOf[A].members.collect{ case m: ru.MethodSymbol if m.isGetter => m }.map{
    it => s"${it.name.toString} ${convertidorDeTipos(it.returnType)}"
  }.toSeq.reverse

  def createSchema:String = {
    s"CREATE TABLE ${this.getClass.getSimpleName}( ${fieldSymbols.mkString(",")} )"
  }

  def convertidorDeTipos(algo:Type ) =
    algo.toString match  {
      case "scala.Char" => "VARCHAR(255)"
      case "scala.Int" => "INT"
      case _ => "VARCHAR(255)"
    }
}
