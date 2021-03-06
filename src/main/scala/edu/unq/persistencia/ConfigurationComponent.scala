package edu.unq.persistencia

import org.hibernate.cfg.Configuration
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.{Session, SessionFactory}
import javax.persistence.{EntityManager, Persistence, EntityManagerFactory}

trait ApplicationConfig {
  val sessionFactory :SessionFactory
}

trait SessionProvider {
  def session :Session
//  val em: EntityManager
}

object ConfiguracionDeDevelopment extends ApplicationConfig {
  val mappingsPath = "entities/"
  val hibernateConfigTag = "JpaScala"
  val configuration :Configuration = {
    val cfg: Configuration = new Configuration()
    cfg.addResource(s"${mappingsPath}Asientos.hbm.xml")
    cfg.configure()
  }

  val builder:StandardServiceRegistryBuilder  = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties)
  val sessionFactory :SessionFactory = configuration.buildSessionFactory(builder.build())
}

trait SessionProviderComponent {
  val sessionProvider:SessionProvider
}

trait DefaultSessionProviderComponent extends SessionProviderComponent{
  
  implicit val sessionProvider:SessionProvider = DefaultSessionProvider
  
  object DefaultSessionProvider extends SessionProvider {
    val sessionFactory :SessionFactory = ConfiguracionDeDevelopment.sessionFactory
    override def session :Session = {
      if(!ConfiguracionDeDevelopment.sessionFactory.getCurrentSession.isOpen){
        ConfiguracionDeDevelopment.sessionFactory.openSession()
      }
      ConfiguracionDeDevelopment.sessionFactory.getCurrentSession
    }

//    val entityManagerFactory: EntityManagerFactory = Persistence.createEntityManagerFactory( ConfiguracionDeDevelopment.hibernateConfigTag )
//    val em: EntityManager = entityManagerFactory.createEntityManager()
  }
  
}