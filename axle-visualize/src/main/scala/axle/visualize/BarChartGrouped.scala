package axle.visualize

import java.awt.Color
import java.awt.Color.blue
import java.awt.Color.green
import java.awt.Color.orange
import java.awt.Color.pink
import java.awt.Color.red
import java.awt.Color.yellow

import scala.reflect.ClassTag

import axle.Show
import axle.algebra.LengthSpace
import axle.algebra.Plottable
import axle.algebra.Tics
import axle.quanta.AngleConverter
import spire.algebra.Eq
import spire.algebra.Order

object BarChartGrouped {

  implicit def drawBarChartGrouped[G: Show, S: Show, Y: Plottable: Tics: Order: Eq, D: ClassTag](
    implicit yls: LengthSpace[Y, _]): Draw[BarChartGrouped[G, S, Y, D]] =
    new Draw[BarChartGrouped[G, S, Y, D]] {
      def component(barChart: BarChartGrouped[G, S, Y, D]) = BarChartGroupedComponent(barChart)
    }

}

case class BarChartGrouped[G: Show, S: Show, Y: Plottable, D](
  initialValue: D,
  drawKey: Boolean = true,
  width: Int = 700,
  height: Int = 600,
  border: Int = 50,
  barWidthPercent: Double = 0.80,
  keyLeftPadding: Int = 20,
  keyTopPadding: Int = 50,
  keyWidth: Int = 80,
  title: Option[String] = None,
  normalFontName: String = "Courier New",
  normalFontSize: Int = 12,
  titleFontName: String = "Palatino",
  titleFontSize: Int = 20,
  xAxis: Y,
  xAxisLabel: Option[String] = None,
  yAxisLabel: Option[String] = None,
  colors: Seq[Color] = List(blue, red, green, orange, pink, yellow))(
    implicit val groupedDataView: GroupedDataView[G, S, Y, D])
