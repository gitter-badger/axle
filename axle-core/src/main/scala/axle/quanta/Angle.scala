package axle.quanta

import scala.math.{ Pi => π }

import axle.algebra.Bijection
import axle.algebra.DirectedGraph
import spire.algebra.Eq
import spire.algebra.Field
import spire.math.Rational

case class Angle() extends Quantum {

  def wikipediaUrl: String = "http://en.wikipedia.org/wiki/Degree_(angle)"

}

abstract class AngleMetadata[N: Field: Eq, DG[_, _]: DirectedGraph]
  extends QuantumMetadataGraph[Angle, N, DG] {

  type U = UnitOfMeasurement[Angle, N]

  def radian: U
  def rad: U
  def degree: U
  def circleDegrees: U
  def circleRadians: U

  //def °: UnitOfMeasurement[Angle, N]

}

object Angle {

  def metadata[N: Field: Eq, DG[_, _]: DirectedGraph] = new AngleMetadata[N, DG] {

    def unit(name: String, symbol: String, wiki: Option[String] = None) =
      UnitOfMeasurement[Angle, N](name, symbol, wiki)

    lazy val _degree = unit("degree", "°", Some("http://en.wikipedia.org/wiki/Degree_(angle)"))
    lazy val _radian = unit("radian", "rad", Some("http://en.wikipedia.org/wiki/Radian"))
    lazy val _circleDegrees = unit("circleDegrees", "circle", Some("http://en.wikipedia.org/wiki/Circle"))
    lazy val _circleRadians = unit("circleRadians", "circle", Some("http://en.wikipedia.org/wiki/Circle"))

    def radian = _radian
    def rad = _radian
    def degree = _degree
    def circleDegrees = _circleDegrees
    def circleRadians = _circleRadians

    //  def clockwise90[N: Field: Eq] = -90 *: °[N]
    //  def counterClockwise90[N: Field: Eq] = 90 *: °[N]

    def units: List[UnitOfMeasurement[Angle, N]] =
      List(_degree, _radian, _circleDegrees, _circleRadians)

    def links: Seq[(UnitOfMeasurement[Angle, N], UnitOfMeasurement[Angle, N], Bijection[N, N])] =
      List[(UnitOfMeasurement[Angle, N], UnitOfMeasurement[Angle, N], Bijection[N, N])](
        (_degree, _circleDegrees, ScaleInt(360)),
        (_radian, _circleRadians, ScaleDouble(2 * π)),
        (_circleDegrees, _circleRadians, BijectiveIdentity[N]))

  }

}