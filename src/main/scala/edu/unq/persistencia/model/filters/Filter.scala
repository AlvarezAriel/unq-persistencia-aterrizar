package edu.unq.persistencia.model.filters

import edu.unq.persistencia.model.{Identificable, Entity}
import org.hibernate.{Session, Criteria}
import org.hibernate.criterion.{Order, Criterion, Restrictions}
import org.joda.time.DateTime
import scala.beans.BeanProperty
import scala.collection.mutable
import scala.collection.JavaConversions._

class Search(@BeanProperty var filter:Filter, @BeanProperty var order:OrderBy) extends Entity[Search] {
    def orderBy(propertyName:Symbol) = {
        this.order = new OrderBy(propertyName.toString().replace("'",""))
        this
    }

    def buildCriteria[T <: Entity[T]](aClass:Class[T])(implicit session:Session) = {
        order.addOrderTo(session.createCriteria(aClass).add(filter.buildCriterion))
    }
}

abstract class Filter extends Entity[Filter]{
    def buildCriterion:Criterion
    def build(c:Criteria) = c.add(this.buildCriterion)
}

class OrderBy(@BeanProperty var propertyName:String) extends Entity[OrderBy] {
    def addOrderTo(c:Criteria):Criteria = {c.addOrder(Order.asc(propertyName)); c}
}

object NoOrder extends OrderBy("") {override def addOrderTo(c:Criteria):Criteria = c}

class FilterExpression(
                       @BeanProperty var propertyName:String = "",
                       @BeanProperty var propertyValue:String,
                       @BeanProperty var propertyType:String
) extends Filter {
    override def buildCriterion: Criterion = Restrictions.eq(propertyName,decodeValue)
    def decodeValue:Any = propertyType match {
        case "date" => DateTime.parse(propertyValue)
        case "long" => propertyValue.toLong
        case _ => propertyValue
    }
}

abstract class CompoundCondition(@BeanProperty var filters:java.util.Set[Filter] = mutable.Set.empty[Filter]) extends Filter

class OR extends CompoundCondition {
    def buildCriterion:Criterion = Restrictions.or(filters.map(_.buildCriterion).toSeq:_*)
}

class AND extends CompoundCondition { def buildCriterion:Criterion = Restrictions.and(filters.map(_.buildCriterion).toSeq:_*) }



// DSL Builder
object Filter {
    implicit def filterToCriteria(filter:Filter):Criterion = filter.buildCriterion
    implicit class FilterOperators(filter:Filter){
        def ||(otherFilter:Filter):Filter = { OR(filter, otherFilter) }
        def &&(otherFilter:Filter):Filter = { AND(filter, otherFilter) }
    }
    implicit class SymbolsToExpressions(tuple:(Symbol,ColumnType)){
        def =?(a:Any):Filter = new FilterExpression(
            propertyName = tuple._1.toString(),
            propertyType = tuple._2.stringName,
            propertyValue = a.toString
        )

        def =?(a:Identificable):Filter = new FilterExpression(
            propertyName = s"${tuple._1.toString().replace("'","")}.id",
            propertyType = tuple._2.stringName,
            propertyValue = a.id.toString
        )
    }
}

//CONSTRUCTORES
object Search{
    def apply(filter:Filter) = {val s=new Search(filter, NoOrder);s;}
}

trait ColumnType { val stringName:String}
object DATE extends ColumnType {val stringName = "date"}
object ID   extends ColumnType {val stringName = "long"}

object OR {def apply(filters:Filter*) = { val or = new OR(); or.filters = filters.toSet[Filter]; or}}
object AND {def apply(filters:Filter*) = { val and = new AND(); and.filters = filters.toSet[Filter]; and}}
