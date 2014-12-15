package axle.algebra

import axle.string
import spire.algebra._
import spire.implicits._

/*
trait Matrix[M[_]] {

  // TODO: T should be convertable to/from Double

  def rows[T](m: M[T]): Int

  def columns[T](m: M[T]): Int

  def length[T](m: M[T]): Int

  def get[T](m: M[T])(i: Int, j: Int): T

  def slice[T](m: M[T])(rs: Seq[Int], cs: Seq[Int]): M[T]

  def toList[T](m: M[T]): List[T]

  def column[T](m: M[T])(j: Int): M[T]
  def row[T](m: M[T])(i: Int): M[T]

  def isEmpty[T](m: M[T]): Boolean
  def isRowVector[T](m: M[T]): Boolean
  def isColumnVector[T](m: M[T]): Boolean
  def isVector[T](m: M[T]): Boolean
  def isSquare[T](m: M[T]): Boolean
  def isScalar[T](m: M[T]): Boolean

  def dup[T](m: M[T]): M[T]
  def negate[T](m: M[T]): M[T]
  def transpose[T](m: M[T]): M[T]
  def diag[T](m: M[T]): M[T]
  def invert[T](m: M[T]): M[Double]
  def ceil[T](m: M[T]): M[Int]
  def floor[T](m: M[T]): M[Int]
  def log[T](m: M[T]): M[Double]
  def log10[T](m: M[T]): M[Double]

  def fullSVD[T](m: M[T]): (M[T], M[T], M[T]) // (U, S, V) such that A = U * diag(S) * V' // TODO: all Matrix[Double] ?
  // def truth: M[Boolean]

  def pow[T](m: M[T])(p: Double): M[Double]

  def addScalar[T](m: M[T])(x: T): M[T]
  def addAssignment[T](m: M[T])(r: Int, c: Int, v: T): M[T]
  def subtractScalar[T](m: M[T])(x: T): M[T]
  def multiplyScalar[T](m: M[T])(x: T): M[T]
  def divideScalar[T](m: M[T])(x: T): M[T]
  def mulRow[T](m: M[T])(i: Int, x: T): M[T]
  def mulColumn[T](m: M[T])(i: Int, x: T): M[T]

  // Operations on pairs of matrices
  // TODO: add and subtract don't make sense for T = Boolean

  def addMatrix[T](m: M[T])(other: M[T]): M[T]
  def subtractMatrix[T](m: M[T])(other: M[T]): M[T]
  def multiplyMatrix[T](m: M[T])(other: M[T]): M[T]
  def mulPointwise[T](m: M[T])(other: M[T]): M[T]
  def divPointwise[T](m: M[T])(other: M[T]): M[T]
  def concatenateHorizontally[T](m: M[T])(right: M[T]): M[T]
  def concatenateVertically[T](m: M[T])(under: M[T]): M[T]
  def solve[T](m: M[T])(B: M[T]): M[T] // returns X, where this === A and A x X = B

  // Operations on a matrix and a column/row vector

  def addRowVector[T](m: M[T])(row: M[T]): M[T]
  def addColumnVector[T](m: M[T])(column: M[T]): M[T]
  def subRowVector[T](m: M[T])(row: M[T]): M[T]
  def subColumnVector[T](m: M[T])(column: M[T]): M[T]
  def mulRowVector[T](m: M[T])(row: M[T]): M[T]
  def mulColumnVector[T](m: M[T])(column: M[T]): M[T]
  def divRowVector[T](m: M[T])(row: M[T]): M[T]
  def divColumnVector[T](m: M[T])(column: M[T]): M[T]

  // Operations on pair of matrices that return M[Boolean]

  def lt[T](m: M[T])(other: M[T]): M[Boolean]
  def le[T](m: M[T])(other: M[T]): M[Boolean]
  def gt[T](m: M[T])(other: M[T]): M[Boolean]
  def ge[T](m: M[T])(other: M[T]): M[Boolean]
  def eq[T](m: M[T])(other: M[T]): M[Boolean]
  def ne[T](m: M[T])(other: M[T]): M[Boolean]

  def and[T](m: M[T])(other: M[T]): M[Boolean]
  def or[T](m: M[T])(other: M[T]): M[Boolean]
  def xor[T](m: M[T])(other: M[T]): M[Boolean]
  def not[T](m: M[T]): M[Boolean]

  // various mins and maxs

  def max[T](m: M[T]): T
  def argmax[T](m: M[T]): (Int, Int)
  def min[T](m: M[T]): T
  def argmin[T](m: M[T]): (Int, Int)

  def rowSums[T](m: M[T]): M[T]
  def columnSums[T](m: M[T]): M[T]
  def columnMins[T](m: M[T]): M[T]
  def columnMaxs[T](m: M[T]): M[T]
  // def columnArgmins
  // def columnArgmaxs

  def columnMeans[T](m: M[T]): M[T]
  def sortColumns[T](m: M[T]): M[T]

  def rowMins[T](m: M[T]): M[T]
  def rowMaxs[T](m: M[T]): M[T]
  def rowMeans[T](m: M[T]): M[T]
  def sortRows[T](m: M[T]): M[T]

  def plus[T](x: M[T])(y: M[T]): M[T]

  def matrix[T](r: Int, c: Int, values: Array[T])(implicit fp: FunctionPair[Double, T]): M[T]

  def matrix[T](m: Int, n: Int, topleft: => T, left: Int => T, top: Int => T, fill: (Int, Int, T, T, T) => T)(implicit fp: FunctionPair[Double, T]): M[T]

  def matrix[T](m: Int, n: Int, f: (Int, Int) => T)(implicit fp: FunctionPair[Double, T]): M[T]

  // Higher-order methods

  def map[B, T](m: M[T])(f: T => B)(implicit fpB: FunctionPair[Double, B]): M[B]

  def flatMapColumns[T, A](m: M[T])(f: M[T] => M[A])(implicit fpA: FunctionPair[Double, A]): M[A]

  def foldLeft[A, T](m: M[T])(zero: M[A])(f: (M[A], M[T]) => M[A]): M[A] =
    (0 until columns(m)).foldLeft(zero)((x: M[A], c: Int) => f(x, column(m)(c)))

  def foldTop[A, T](m: M[T])(zero: M[A])(f: (M[A], M[T]) => M[A]): M[A] =
    (0 until rows(m)).foldLeft(zero)((x: M[A], r: Int) => f(x, row(m)(r)))

  /**
   * Hilbert matrix
   */
  def hilb(n: Int) = matrix[Double](n, n, (r: Int, c: Int) => 1D / (r + c + 1))

  def median(m: M[Double]): M[Double] = {
    val sorted = sortColumns(m)
    if (rows(m) % 2 === 0) {
      val left = row(sorted)(rows(m) / 2 - 1)
      val right = row(sorted)(rows(m) / 2)
      divideScalar(plus(left)(right))(2d)
    } else {
      row(sorted)(rows(m) / 2)
    }
  }

  def centerRows[T](m: M[T]): M[T]
  def centerColumns[T](m: M[T]): M[T]

  def rowRange[T](m: M[T]): M[T]
  def columnRange[T](m: M[T]): M[T]

  def sumsq[T](m: M[T]): M[T]

  def cov(m: M[Double]): M[Double]

  def std(m: M[Double]): M[Double]

  def zscore(m: M[Double]): M[Double]

  def pca(Xnorm: M[Double], cutoff: Double): (M[Double], M[Double])

  def numComponentsForCutoff(s: M[Double], cutoff: Double): Int

  def zeros[T](m: Int, n: Int)(implicit fp: FunctionPair[Double, T]): M[T]
  def ones[T](m: Int, n: Int)(implicit fp: FunctionPair[Double, T]): M[T]
  def eye[T](n: Int)(implicit fp: FunctionPair[Double, T]): M[T]
  def I[T](n: Int)(implicit fp: FunctionPair[Double, T]): M[T]
  def rand[T](m: Int, n: Int)(implicit fp: FunctionPair[Double, T]): M[T]
  def randn[T](m: Int, n: Int)(implicit fp: FunctionPair[Double, T]): M[T]
  def falses(m: Int, n: Int): M[Boolean]
  def trues(m: Int, n: Int): M[Boolean]

}

object Matrix {

  import axle.Show

  implicit def showMatrix[M[_]: Matrix, T: Show](implicit fp: FunctionPair[Double, T]): Show[M[T]] = new Show[M[T]] {

    val witness = implicitly[Matrix[M]]

    def text(m: M[T]): String = {
      (0 until witness.rows(m)).map(i => (0 until witness.columns(m)).map(j => string(witness.get(m)(i, j))).mkString(" ")).mkString("\n")
    }
  }

}
*/