package edu.unq.persistencia.model.empresa

import org.joda.time.DateTime

class Tramo(
  var origen:Locacion,
  var destino:Locacion,
  var salida:DateTime,
  var llegada:DateTime,
  var precioBase:BigDecimal
) extends Identificable
