package axle.quanta

import axle.algebra.Bijection
import spire.algebra.Eq
import spire.algebra.Field
import spire.algebra.MultiplicativeMonoid

trait UnitConverter[Q, N] { self =>

  def frameOfReference(implicit fieldN: Field[N], eqN: Eq[N]) =
    modulize[N, Q](fieldN, eqN, self)

  def defaultUnit: UnitOfMeasurement[Q]

  def units: List[UnitOfMeasurement[Q]]

  def links: Seq[(UnitOfMeasurement[Q], UnitOfMeasurement[Q], Bijection[N, N])]

  def convert(orig: UnittedQuantity[Q, N], newUnit: UnitOfMeasurement[Q])(implicit ev: MultiplicativeMonoid[N], ev2: Eq[N]): UnittedQuantity[Q, N]
}
