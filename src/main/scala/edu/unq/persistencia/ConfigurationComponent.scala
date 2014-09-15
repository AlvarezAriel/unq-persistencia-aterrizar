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
  val mappingsPath = "entities/"
  val hibernateConfigTag = "JpaScala"
  val configuration :Configuration = {
    val cfg: Configuration = new Configuration()
    cfg.configure()
//    cfg.addResource(s"${mappingsPath}Asientos.hbm.xml")
  }

  val builder:StandardServiceRegistryBuilder  = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties)
  val sessionFactory :SessionFactory = configuration.buildSessionFactory(builder.build())
}

trait SessionProviderComponent {
  val sessionProvider:SessionProvider
}
trait DefaultSessionProviderComponent extends SessionProviderComponent{
  
  val sessionProvider:SessionProvider = DefaultSessionProvider
  
  object DefaultSessionProvider extends SessionProvider {
    val sessionFactory :SessionFactory = ConfiguracionDeDevelopment.sessionFactory
    val session :Session = ConfiguracionDeDevelopment.sessionFactory.getCurrentSession
    val entityManagerFactory: EntityManagerFactory = Persistence.createEntityManagerFactory( ConfiguracionDeDevelopment.hibernateConfigTag )
    val em: EntityManager = entityManagerFactory.createEntityManager()  
  }
  
}