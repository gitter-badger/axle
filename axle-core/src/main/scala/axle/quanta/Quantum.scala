package axle.quanta

/**
 * Quantum
 *
 * Used in the sense of the World English Dictionary's 4th definition:
 *
 * 4. something that can be quantified or measured
 *
 * [[http://dictionary.reference.com/browse/quantum]]
 *
 */

import spire.algebra.Field
import spire.algebra.Eq
import axle.algebra.Bijection
import axle.algebra.DirectedGraph
import axle.algebra.Vertex
import spire.implicits._
import axle.syntax.directedgraph._

trait Quantum {

  type Q <: Quantum

  type CG[DG[_, _], N] = DG[UnitOfMeasurement[Q, N], N => N]

  def units[N: Field: Eq]: List[UnitOfMeasurement[Q, N]]

  def links[N: Field: Eq]: Seq[(UnitOfMeasurement[Q, N], UnitOfMeasurement[Q, N], Bijection[N, N])]

  private[quanta] def conversions[N: Field: Eq, DG[_, _]](vps: Seq[UnitOfMeasurement[Q, N]], ef: Seq[Vertex[UnitOfMeasurement[Q, N]]] => Seq[(Vertex[UnitOfMeasurement[Q, N]], Vertex[UnitOfMeasurement[Q, N]], N => N)])(implicit evDG: DirectedGraph[DG]): DG[UnitOfMeasurement[Q, N], N => N] =
    evDG.make(vps, ef)

  private[quanta] def trip2fns[N: Field: Eq](trip: (Vertex[UnitOfMeasurement[Q, N]], Vertex[UnitOfMeasurement[Q, N]], N)): Seq[(Vertex[UnitOfMeasurement[Q, N]], Vertex[UnitOfMeasurement[Q, N]], N => N)] = {
    val (from, to, multiplier) = trip
    Vector(
      (from, to, _ * multiplier),
      (to, from, _ / multiplier))
  }

  private[quanta] def trips2fns[N: Field: Eq](trips: Seq[(Vertex[UnitOfMeasurement[Q, N]], Vertex[UnitOfMeasurement[Q, N]], N)]) =
    trips.flatMap(trip2fns(_))

  private[quanta] def byName[N: Field: Eq, DG[_, _]: DirectedGraph](cg: CG[DG, N], unitName: String): UnitOfMeasurement[Q, N] =
    cg.findVertex(_.payload.name === unitName).get.payload

  def cgnDisconnected[N: Field: Eq, DG[_, _]: DirectedGraph]: CG[DG, N] = conversions(units, (vs: Seq[Vertex[UnitOfMeasurement[Q, N]]]) => Nil)

  implicit def cgn[N: Field: Eq, DG[_, _]: DirectedGraph]: CG[DG, N] = conversions(
    units,
    (vs: Seq[Vertex[UnitOfMeasurement[Q, N]]]) => {
      val name2vertex = vs.map(v => (v.payload.name, v)).toMap
      links[N].flatMap({
        case (x, y, bijection) => {
          val xv = name2vertex(x.name)
          val yv = name2vertex(y.name)
          List((xv, yv, bijection.apply _), (yv, xv, bijection.unapply _))
        }
      })
    })

}
