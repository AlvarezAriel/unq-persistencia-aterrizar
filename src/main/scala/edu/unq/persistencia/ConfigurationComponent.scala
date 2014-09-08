package edu.unq.persistencia

import org.hibernate.cfg.Configuration
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.{Session, SessionFactory}
import javax.persistence.{EntityManager, Persistence, EntityManagerFactory}

trait ApplicationConfig {
  val sessionFactory :SessionFactory
}

trait SessionProvider {
  val session :Session
  val em: EntityManager
}

object ConfiguracionDeDevelopment extends ApplicationConfig {
  val hibernateConfigTag = "JpaScala"
  val configuration :Configuration = new Configuration().configure()
  val builder:StandardServiceRegistryBuilder  = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties)
  val sessionFactory :SessionFactory = configuration.buildSessionFactory(builder.build())
}

trait DefaultSessionProviderComponent {
  
  val sessionProvider:SessionProvider = DefaultSessionProvider
  
  object DefaultSessionProvider extends SessionProvider {
    val sessionFactory :SessionFactory = ConfiguracionDeDevelopment.sessionFactory
    val session :Session = ConfiguracionDeDevelopment.sessionFactory.getCurrentSession
    val entityManagerFactory: EntityManagerFactory = Persistence.createEntityManagerFactory( ConfiguracionDeDevelopment.hibernateConfigTag )
    val em: EntityManager = entityManagerFactory.createEntityManager()  
  }
  
}