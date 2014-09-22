package edu.unq.persistencia.model

/**
 * Created by nahuel on 22/09/
 */
  import scala.{Array => A}
  import org.hibernate._
  import org.hibernate.usertype._
  import org.hibernate.`type`._
import java.sql.{ResultSet, PreparedStatement}

//  import java.sql._
  import java.io._
import org.hibernate.engine.spi.SessionImplementor

class CategoriaMapper extends UserType{
  def nullableType: StringType = StringType.INSTANCE
  val mapeo = Map(
      Primera.getClass.toString -> Primera,
      Business.getClass.toString -> Business,
      Turista.getClass.toString -> Turista
  )

  
    def returnedClass = classOf[Option[_]]
    
    
    def sqlTypes = A(nullableType.sqlType)

    def isMutable = false
    
    def equals(x: Object, y: Object) = x.equals(y)
    
    def hashCode(x: Object) = x.hashCode
    
    def deepCopy(value: Object) = value
    
    def replace(original: Object, target: Object, owner: Object) = original
    
    def disassemble(value: Object) = value.asInstanceOf[Serializable]
    
    def assemble(cached: Serializable, owner: Object) :Object = cached.asInstanceOf[Object]

  override def nullSafeSet(st: PreparedStatement, value: scala.Any, index: Int, session: SessionImplementor): Unit = {
      nullableType.nullSafeSet(st, if (value == null) null else value.getClass.toString, index, session)
  }

  override def nullSafeGet(rs: ResultSet, names: Array[String], session: SessionImplementor, owner: scala.Any): AnyRef = {
    mapeo(rs.getString(names(0)))
  }
}
