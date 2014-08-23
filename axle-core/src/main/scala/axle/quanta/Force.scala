package axle.quanta

import spire.algebra._
import spire.math._
import spire.implicits._
import axle.graph._

abstract class Force[N: Field: Order: Eq](space: MetricSpace[N, Double]) extends Quantum[N](space) {
  
  class ForceQuantity(
    magnitude: N = field.one,
    _unit: Option[Q] = None,
    _name: Option[String] = None,
    _symbol: Option[String] = None,
    _link: Option[String] = None) extends Quantity(magnitude, _unit, _name, _symbol, _link)

  type Q = ForceQuantity

  implicit def eqTypeclass: Eq[Q] = new Eq[Q] {
    def eqv(x: Q, y: Q): Boolean =
      (x.magnitude === y.magnitude) &&
        ((x.unitOption.isDefined && y.unitOption.isDefined && (x.unitOption.get === y.unitOption.get)) ||
          (x.unitOption.isEmpty && y.unitOption.isEmpty && x.equals(y)))
  }

  def newUnitOfMeasurement(
    name: Option[String] = None,
    symbol: Option[String] = None,
    link: Option[String] = None): ForceQuantity =
    new ForceQuantity(field.one, None, name, symbol, link)

  def newQuantity(magnitude: N, unit: ForceQuantity): ForceQuantity =
    new ForceQuantity(magnitude, Some(unit), None, None, None)

  val wikipediaUrl = "http://en.wikipedia.org/wiki/Force"

}

object Force extends Force[Rational](rationalDoubleMetricSpace) {

//  def vps() = List(
//    unit("pound", "lb", Some("http://en.wikipedia.org/wiki/Pound-force")),
//    unit("newton", "N", Some("http://en.wikipedia.org/wiki/Newton_(unit)")),
//    unit("dyne", "dyn", Some("http://en.wikipedia.org/wiki/Dyne"))
//  )
//
//  def ef() = (vs: Seq[DirectedGraphVertex[ForceQuantity]]) => vs match {
//    case Nil => withInverses(List())
//  }

  lazy val _conversionGraph = conversions(
    List(
      unit("pound", "lb", Some("http://en.wikipedia.org/wiki/Pound-force")),
      unit("newton", "N", Some("http://en.wikipedia.org/wiki/Newton_(unit)")),
      unit("dyne", "dyn", Some("http://en.wikipedia.org/wiki/Dyne"))
    ),
    (vs: Seq[Vertex[ForceQuantity]]) => vs match {
      case pound :: newton :: dyne :: Nil => trips2fns(List())
    }
  )

  lazy val pound = byName("pound")
  lazy val newton = byName("newton")
  lazy val dyne = byName("dyne")

  def conversionGraph: DirectedGraph[Q, Rational => Rational] = _conversionGraph
  
}

