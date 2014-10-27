package edu.unq.persistencia.model.filters

import edu.unq.persistencia.model.Entity
import org.hibernate.Criteria
import org.hibernate.criterion.{Order, Criterion, Restrictions}
import org.joda.time.DateTime
import scala.beans.BeanProperty
import scala.collection.mutable
import scala.collection.JavaConversions._

class Search(@BeanProperty var filter:Filter, @BeanProperty var order:OrderBy) extends Entity[Search]

abstract class Filter extends Entity[Filter]{
    def buildCriterion:Criterion
    def build(c:Criteria) = c.add(this.buildCriterion)
}

class OrderBy(@BeanProperty var propertyName:String) extends Entity[OrderBy] {
    def addOrderTo(c:Criteria) = c.addOrder(Order.desc(propertyName))
}

class FilterExpression(
                       @BeanProperty var propertyName:String = "",
                       @BeanProperty var propertyValue:String,
                       @BeanProperty var longValue:Long,
                       @BeanProperty var stringValue:String,
                       @BeanProperty var dateValue:DateTime
) extends Filter {
    override def buildCriterion: Criterion = Restrictions.eq(propertyName,propertyValue)
}

abstract class CompoundCondition(@BeanProperty var filters:java.util.List[Filter] = mutable.Seq.empty[Filter]) extends Filter

class OR extends CompoundCondition {
    def buildCriterion:Criterion = Restrictions.or(filters.map(_.buildCriterion):_*)
}

class AND extends CompoundCondition { def buildCriterion:Criterion = Restrictions.and(filters.map(_.buildCriterion):_*) }

object Filter {
    implicit def filterToCriteria(filter:Filter):Criterion = filter.buildCriterion
}

