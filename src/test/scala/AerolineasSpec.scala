import org.scalatest.{BeforeAndAfter, Matchers, FlatSpec}

class AerolineasSpec  extends FlatSpec with Matchers with BeforeAndAfter {

  before {
    new
  }

  after {

  }

  "Encuesta Service, para un año dado, " should "saber la cantidad de ventas totales" in {
    200 should be === 200
  }
}
