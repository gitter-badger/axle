package axle.algebra

import scala.annotation.implicitNotFound

import spire.algebra.Field
import spire.algebra.Module
import spire.algebra.Ring

@implicitNotFound("Witness not found for LinearAlgebra[${M}, ${RowT}, ${ColT}, ${T}]")
trait LinearAlgebra[M, RowT, ColT, T] {

  def ring: Ring[M]

  def elementField: Field[T]

  def endofunctor: Endofunctor[M, T]

  def module: Module[M, T]

  def rows(m: M): RowT

  def columns(m: M): ColT

  def length(m: M): Int // TODO

  def get(m: M)(i: RowT, j: ColT): T

  def slice(m: M)(rs: Seq[RowT], cs: Seq[ColT]): M

  def toList(m: M): List[T]

  def column(m: M)(j: ColT): M
  def row(m: M)(i: RowT): M

  def isEmpty(m: M): Boolean
  def isRowVector(m: M): Boolean
  def isColumnVector(m: M): Boolean
  def isVector(m: M): Boolean
  def isSquare(m: M): Boolean
  def isScalar(m: M): Boolean

  def dup(m: M): M

  def negate(m: M): M

  def transpose(m: M): M
  def diag(m: M): M
  def invert(m: M): M
  def ceil(m: M): M
  def floor(m: M): M
  def log(m: M): M
  def log10(m: M): M

  def fullSVD(m: M): (M, M, M) // (U, S, V) such that A = U * diag(S) * V' // TODO: all Matrix[Double] ?
  // def truth: M[Boolean]

  def pow(m: M)(p: Double): M

  def addScalar(m: M)(x: T): M
  def subtractScalar(m: M)(x: T): M

  // TODO: from Module:
  //  def multiplyScalar(m: M)(x: T): M
  def divideScalar(m: M)(x: T): M

  def addAssignment(m: M)(r: RowT, c: ColT, v: T): M
  def mulRow(m: M)(i: RowT, x: T): M
  def mulColumn(m: M)(i: ColT, x: T): M

  // Operations on pairs of matrices

  def mulPointwise(m: M)(other: M): M
  def divPointwise(m: M)(other: M): M

  def zipWith(m: M)(op: (T, T) => T)(other: M): M
  def reduceToScalar(m: M)(op: (T, T) => T): T

  def concatenateHorizontally(m: M)(right: M): M
  def concatenateVertically(m: M)(under: M): M
  def solve(m: M)(B: M): M // returns X, where this === A and A x X = B

  // Operations on a matrix and a column/row vector

  def addRowVector(m: M)(row: M): M
  def addColumnVector(m: M)(column: M): M
  def subRowVector(m: M)(row: M): M
  def subColumnVector(m: M)(column: M): M
  def mulRowVector(m: M)(row: M): M
  def mulColumnVector(m: M)(column: M): M
  def divRowVector(m: M)(row: M): M
  def divColumnVector(m: M)(column: M): M

  // Operations on pair of matrices that return M[Boolean]

  def lt(m: M)(other: M): M
  def le(m: M)(other: M): M
  def gt(m: M)(other: M): M
  def ge(m: M)(other: M): M
  def eq(m: M)(other: M): M
  def ne(m: M)(other: M): M

  def and(m: M)(other: M): M
  def or(m: M)(other: M): M
  def xor(m: M)(other: M): M
  def not(m: M): M

  // various mins and maxs

  def max(m: M): T
  def argmax(m: M): (RowT, ColT)
  def min(m: M): T
  def argmin(m: M): (RowT, ColT)

  def rowSums(m: M): M
  def columnSums(m: M): M
  def columnMins(m: M): M
  def columnMaxs(m: M): M
  // def columnArgmins
  // def columnArgmaxs

  def columnMeans(m: M): M
  def sortColumns(m: M): M

  def rowMins(m: M): M
  def rowMaxs(m: M): M
  def rowMeans(m: M): M
  def sortRows(m: M): M

  def matrix(r: RowT, c: ColT, values: Array[T]): M

  def matrix(m: RowT, n: ColT, topleft: => T, left: RowT => T, top: ColT => T, fill: (RowT, ColT, T, T, T) => T): M

  def matrix(m: RowT, n: ColT, f: (RowT, ColT) => T): M

  // Higher-order methods

  def flatMapColumns(m: M)(f: M => M): M

  def foldLeft(m: M)(zero: M)(f: (M, M) => M): M

  def foldTop(m: M)(zero: M)(f: (M, M) => M): M

  /**
   * Hilbert matrix
   */
  //  def hilb(n: Int) = matrix(n, n,
  //    (r: Int, c: Int) => elementField.one / (r + c + elementField.one))

  //  def median(m: M): M = {
  //    val sorted = sortColumns(m)
  //    if (rows(m) % 2 === 0) {
  //      val left = row(sorted)(rows(m) / 2 - 1)
  //      val right = row(sorted)(rows(m) / 2)
  //      divideScalar(plus(left)(right))(2d)
  //    } else {
  //      row(sorted)(rows(m) / 2)
  //    }
  //  }

  def centerRows(m: M): M
  def centerColumns(m: M): M

  def rowRange(m: M): M
  def columnRange(m: M): M

  def sumsq(m: M): M

  def cov(m: M): M

  def std(m: M): M

  def zscore(m: M): M

  def pca(Xnorm: M, cutoff: Double): (M, M)

  def numComponentsForCutoff(s: M, cutoff: Double): Int

  // TODO:
  def zero: M = ring.zero
  def zeros(laRows: RowT, laColumns: ColT): M
  def eye(laRows: RowT): M = ring.one
  def I(laRows: RowT): M = ring.one
  def ones(laRows: RowT, laColumns: ColT): M
  def rand(laRows: RowT, laColumns: ColT): M
  def randn(laRows: RowT, laColumns: ColT): M

}

object LinearAlgebra {

  @inline final def apply[M, RowT, ColT, T](implicit ev: LinearAlgebra[M, RowT, ColT, T]): LinearAlgebra[M, RowT, ColT, T] = ev

}