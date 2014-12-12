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
        Cypher(
        s"""MERGE (user:Usuario {key:${usuarioOrigen.id}, name:"${usuarioOrigen.nombre}"})
            |MERGE (target:Usuario {key:${usuarioTarget.id}, name:"${usuarioTarget.nombre}"})
            |CREATE (user)-[:ENVIO]-> (mensaje:Mensaje {contenido:"$mensaje"} ) <-[:RECIBIO]- (target)
              """.stripMargin).execute()
    }

    def misContactos(usuarioOrigen:UsuarioEntity)={
        Cypher(
            s"""
            |MATCH (user:Usuario {key:${usuarioOrigen.id}) -[:AMIGO_DE*]-> (otro)
            |RETURN Distinct otro.key
              """.stripMargin).execute()
    }
}
