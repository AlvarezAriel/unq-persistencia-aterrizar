package edu.unq.persistencia.model.filters

import java.util
import javax.persistence.TypedQuery

import edu.unq.persistencia.model.{Vuelo, Identificable, Entity}
import org.hibernate.{Query, Session, Criteria}
import org.hibernate.criterion.{Order, Restrictions}
import org.joda.time.DateTime
import scala.beans.BeanProperty
import scala.collection.mutable
import scala.collection.JavaConversions._

class Search(@BeanProperty var filter:Filter, @BeanProperty var order:FieldName, @BeanProperty var group:FieldName ) extends Entity[Search] {
    def list(implicit session:Session) = {
        val createQuery: Query = session.createQuery(buildQuery)
        val list1: util.List[_] = createQuery.list()
        list1.toSeq.asInstanceOf[Seq[Int]]
    }

    def buildQuery =
      s"""
         | SELECT vuelo.id
         | FROM
         |     Aerolinea as aerolinea
         |     inner join aerolinea.vuelos as vuelo
         |     inner join vuelo.tramos as tramo
         |     inner join tramo.origen as origen
         |     inner join tramo.destino as destino
         |     inner join tramo.llegada as llegada
         |     inner join tramo.salida as salida
         |     inner join tramo.asientos as asiento
         | WHERE
         |     ${filter.build}
         | ${group.build("GROUP")}
         | ${order.build("ORDER")}
       """.stripMargin

    def orderBy(campo:String ) = { order = new FieldName(campo); this }
    def groupBy(campo:String ) = { group = new FieldName(campo); this }
    def where(filtro:Filter)   = { filter = filtro; this}
}

object VuelosValueContainer {
    val vuelos = Search(EmptyFilterExpression)
}
object Select {
    def all(busqueda:Search) = busqueda
}

abstract class Filter extends Entity[Filter]{
    def build = ""//c.add(this.buildString)
}

class FieldName(@BeanProperty var propertyName:String) extends Entity[FieldName] {
    def build(regla:String) = s"$regla BY $propertyName"
}

object EmptyField extends FieldName("") {override def build(e:String) = ""}

object EmptyFilterExpression extends FilterExpression("","") { override def build = "" }

class FilterExpression(
                       @BeanProperty var propertyName:String = "",
                       @BeanProperty var propertyValue:String
) extends Filter {
    override def build: String = s"($propertyName = $propertyValue)"
}

abstract class CompoundCondition(@BeanProperty var filters:java.util.Set[Filter] = mutable.Set.empty[Filter]) extends Filter{
    def buildPartial = filters.map(_.build).mkString(_:String)
}

class OR extends CompoundCondition { override def build:String = buildPartial(" OR ") }

class AND extends CompoundCondition { override def build:String = buildPartial(" AND ") }

// DSL Builder
object Filter {
    implicit def filterToString(filter:Filter):String = filter.build
    implicit class FilterOperators(filter:Filter){
        def ||(otherFilter:Filter):Filter = { OR(filter, otherFilter) }
        def &&(otherFilter:Filter):Filter = { AND(filter, otherFilter) }
    }

    implicit class SymbolsToExpressions(fieldName:String){
        def =?(value:Any):Filter = new FilterExpression(fieldName,value.toString)
    }
}

//CONSTRUCTORES
object Search{
    def apply(filter:Filter) = {val s=new Search(filter, EmptyField, EmptyField);s;}
}

object OR {def apply(filters:Filter*) = { val or = new OR(); or.filters = filters.toSet[Filter]; or}}
object AND {def apply(filters:Filter*) = { val and = new AND(); and.filters = filters.toSet[Filter]; and}}
