package edu.unq.persistencia.model.filters

import org.hibernate.criterion.{Criterion, Restrictions}
import org.joda.time.DateTime
import scala.collection.mutable
import scala.collection.JavaConversions._

trait Filter {
    def build:Criterion
}

class FilterExpression(var propertyName:String,
                       var propertyValue:String,
                       var longValue:Long,
                       var stringValue:String,
                       var dateValue:DateTime
) extends Filter {
    def build = Restrictions.eq(propertyName,propertyValue)
}

abstract class CompoundCondition(var filtros:java.util.List[Filter] = mutable.Seq.empty[Filter]) extends Filter

class OR extends CompoundCondition { def build = Restrictions.and(filtros.map(_.build):_*) }

class AND extends CompoundCondition { def build = Restrictions.or(filtros.map(_.build):_*) }




object Filter {
    implicit def filterToCriterion(filter:Filter):Criterion = filter.build
}

import Filter._
