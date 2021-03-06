package axle.visualize

import java.awt.Color
import java.awt.Color.black
import java.awt.Font

import scala.reflect.ClassTag

import axle.Show
import axle.algebra.LengthSpace
import axle.algebra.Tics
import axle.quanta.AngleConverter
import axle.string
import axle.visualize.element.HorizontalLine
import axle.visualize.element.Rectangle
import axle.visualize.element.VerticalLine
import axle.visualize.element.XTics
import axle.visualize.element.YTics
import spire.algebra.Eq
import spire.algebra.Order
import spire.compat.ordering
import spire.implicits.DoubleAlgebra

case class BarChartGroupedView[G: Show, S: Show, Y: Order: Tics: Eq, D: ClassTag](
  chart: BarChartGrouped[G, S, Y, D],
  data: D,
  colorStream: Stream[Color],
  normalFont: Font)(
    implicit yls: LengthSpace[Y, _]) {

  import chart._

  val minX = 0d
  val maxX = 1d
  val yAxis = minX

  val groups = groupedDataView.groups(data)
  val slices = groupedDataView.slices(data)

  val padding = 0.05 // on each side
  val widthPerGroup = (1d - (2 * padding)) / groups.size
  val whiteSpace = widthPerGroup * (1d - barWidthPercent)

  val (dataMinY, dataMaxY) = groupedDataView.yRange(data)
  val minY = List(xAxis, dataMinY).min
  val maxY = List(xAxis, dataMaxY).max

  implicit val ddls = axle.algebra.LengthSpace.doubleDoubleLengthSpace

  val scaledArea = ScaledArea2D(
    width = if (drawKey) width - (keyWidth + keyLeftPadding) else width,
    height,
    border,
    minX, maxX, minY, maxY)

  val vLine = VerticalLine(scaledArea, yAxis, black)
  val hLine = HorizontalLine(scaledArea, xAxis, black)

  val gTics = XTics(
    scaledArea,
    groups.toStream.zipWithIndex.map({ case (g, i) => (padding + (i + 0.5) * widthPerGroup, string(g)) }).toList,
    normalFont,
    false,
    36d *: angleDouble.degree,
    black)

  val yTics = YTics(scaledArea, Tics[Y].tics(minY, maxY), normalFont, black)

  val barSliceWidth = (widthPerGroup - (whiteSpace / 2d)) / slices.size.toDouble

  val bars = for {
    ((s, j), color) <- slices.toVector.zipWithIndex.zip(colorStream)
    (g, i) <- groups.toStream.zipWithIndex
  } yield {
    val leftX = padding + (whiteSpace / 2d) + i * widthPerGroup + j * barSliceWidth
    val rightX = leftX + barSliceWidth
    Rectangle(scaledArea, Point2D(leftX, minY), Point2D(rightX, groupedDataView.valueOf(data, (g, s))), fillColor = Some(color))
  }

}