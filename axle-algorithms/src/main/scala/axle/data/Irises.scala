package axle.data

import java.io.File

import scala.Option.option2Iterable
import scala.sys.process.stringSeqToProcess
import scala.util.Try

import axle.quanta.Distance
import axle.quanta.DistanceConverter
import axle.quanta.UnittedQuantity
import axle.algebra.DirectedGraph
import spire.algebra.Eq
import spire.implicits.DoubleAlgebra
import spire.implicits.moduleOps

/**
 *
 * Description: http://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.names
 *
 * Data url: http://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data
 *
 * Columns:
 *
 *  1. sepal length in cm
 *  2. sepal width in cm
 *  3. petal length in cm
 *  4. petal width in cm
 *  5. class: Iris Setosa, Iris Versicolour, or Iris Virginica
 *
 * Examples:
 *
 *   5.0,3.3,1.4,0.2,Iris-setosa
 *   7.0,3.2,4.7,1.4,Iris-versicolor
 *
 * For examples of analyzing this dataset with R, see
 * http://horicky.blogspot.com/2012/04/machine-learning-in-r-clustering.html
 *
 */

class Irises[DG[_, _]: DirectedGraph](implicit converter: DistanceConverter[Double]) {

  case class Iris(
    sepalLength: UnittedQuantity[Distance, Double],
    sepalWidth: UnittedQuantity[Distance, Double],
    petalLength: UnittedQuantity[Distance, Double],
    petalWidth: UnittedQuantity[Distance, Double],
    species: String)

  object Iris {
    implicit val irisEq = new Eq[Iris] { def eqv(x: Iris, y: Iris) = x equals y }
  }

  val dataUrl = "http://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data"

  val filename = "iris.data"

  val file = new File(filename)

  import converter.centimeter

  if (!file.exists) {
    Seq("wget", "-q", dataUrl, "-O", filename)!!
  }

  val irises = io.Source.fromFile(file).getLines().toList flatMap { line =>
    Try {
      val fields = line.split(",")
      Iris(
        fields(0).toDouble *: centimeter,
        fields(1).toDouble *: centimeter,
        fields(2).toDouble *: centimeter,
        fields(3).toDouble *: centimeter,
        fields(4))
    } toOption
  }

}