package edu.unq.persistencia.services

import edu.unq.persistencia.model.login.UsuarioEntity
import org.anormcypher.{Cypher, Neo4jREST}

class AmigosService {

    implicit val connection = Neo4jREST

    def amigarseCon(usuarioOrigen:UsuarioEntity, usuarioTarget:UsuarioEntity): Unit ={
        Cypher(
             s"""MERGE (user:Usuario {key:${usuarioOrigen.id}, name:"${usuarioOrigen.nombre}"})
                |MERGE (target:Usuario {key:${usuarioTarget.id}, name:"${usuarioTarget.nombre}"})
                |CREATE (user)-[:AMIGO_DE]->(target)
              """.stripMargin).execute()
    }

    def misAmigos(usuarioOrigen:UsuarioEntity):List[Int]={
        Cypher(
          s"""MATCH (user { key:${usuarioOrigen.id} }) -[:AMIGO_DE]-> (target)
             |RETURN target.key
           """.stripMargin)()
            .map( row => row[Int]("target.key")).toList
    }

    def enviarMensaje(usuarioOrigen:UsuarioEntity, usuarioTarget:UsuarioEntity, mensaje:String): Unit ={

    }

    def misContactos(usuarioOrigen:UsuarioEntity)={

    }
}

class EnviadorDeMensajes {

    def enviar(mensaje:String): Unit ={
        //si si... vos sentate tranquilo que te lo envío
        mensaje
    }
}
