package axle.quanta

import axle.algebra.Bijection
import axle.algebra.DirectedGraph
import spire.algebra.Eq
import spire.algebra.Field

case class Mass() extends Quantum {

  def wikipediaUrl: String = "http://en.wikipedia.org/wiki/Orders_of_magnitude_(mass)"
  // "http://en.wikipedia.org/wiki/Mass"

}

trait MassUnits extends QuantumUnits[Mass] {

  lazy val gram = unit("gram", "g")
  lazy val tonne = unit("tonne", "T", Some("http://en.wikipedia.org/wiki/Tonne"))
  lazy val milligram = unit("milligram", "mg")
  lazy val kilogram = unit("kilogram", "Kg")
  lazy val megagram = unit("megagram", "Mg")
  lazy val kilotonne = unit("kilotonne", "KT")
  lazy val megatonne = unit("megatonne", "MT")
  lazy val gigatonne = unit("gigatonne", "GT")
  lazy val teratonne = unit("teratonne", "TT")
  lazy val petatonne = unit("petatonne", "PT")
  lazy val exatonne = unit("exatonne", "ET")
  lazy val zettatonne = unit("zettatonne", "ZT")
  lazy val yottatonne = unit("yottatonne", "YT")

  lazy val man = unit("man", "man", Some("http://en.wikipedia.org/wiki/Body_weight"))

  lazy val earth = unit("earth", "M⊕", Some("http://en.wikipedia.org/wiki/Earth"))
  lazy val sun = unit("sun", "M☉", Some("http://en.wikipedia.org/wiki/Solar_mass"))
  lazy val jupiter = unit("jupiter", "M♃", Some("http://en.wikipedia.org/wiki/Jupiter"))
  lazy val saturn = unit("saturn", "M♄", Some("http://en.wikipedia.org/wiki/Saturn"))
  lazy val neptune = unit("neptune", "M♆", Some("http://en.wikipedia.org/wiki/Neptune"))
  lazy val uranus = unit("uranus", "M♅", Some("http://en.wikipedia.org/wiki/Uranus"))
  lazy val venus = unit("venus", "M♀", Some("http://en.wikipedia.org/wiki/Venus"))
  lazy val mars = unit("mars", "M♂", Some("http://en.wikipedia.org/wiki/Mars"))
  lazy val mercury = unit("mercury", "M☿", Some("http://en.wikipedia.org/wiki/Mercury_(planet)"))
  lazy val pluto = unit("pluto", "M♇", Some("http://en.wikipedia.org/wiki/Pluto"))
  lazy val moon = unit("moon", "M☽", Some("http://en.wikipedia.org/wiki/Moon"))

  // http://en.wikipedia.org/wiki/Astronomical_symbols
  def ⊕ = earth
  def ☼ = sun
  def ☉ = sun
  def ♃ = jupiter
  def ♄ = saturn
  def ♆ = neptune
  def ♅ = uranus
  def ♀ = venus
  def ♂ = mars
  def ☿ = mercury
  def ♇ = pluto
  def ☽ = moon

  //  // sun also = "332950" *: earth
  //  // TODO lazy val milkyWayMass = 5.8E11 *: sun // Some("Milky Way Mass"), None, Some("http://en.wikipedia.org/wiki/Milky_Way"))
  //  // TODO lazy val andromedaMass = 7.1E11 *: sun // Some("Andromeda Mass"), None, Some("http://en.wikipedia.org/wiki/Andromeda_Galaxy"))
  //
  //  // TODO hydrogen atom
  //
  //  // earthunit = 5.9 x 10^24 kg
  //  // 10^24 kg = ^21 t = ^12 gt = ^9 tt = ^6 pt = ^3 et = ^0 zt

  def units: List[UnitOfMeasurement[Mass]] =
    List(gram, tonne, milligram, kilogram, megagram, kilotonne, megatonne, gigatonne, teratonne,
      petatonne, exatonne, zettatonne, yottatonne, man, earth, sun, jupiter, saturn, neptune,
      uranus, venus, mars, mercury, pluto, moon)

}

trait MassConverter[N] extends UnitConverter[Mass, N] with MassUnits

object Mass {

  def converterGraph[N: Field: Eq, DG[_, _]: DirectedGraph] =
    new UnitConverterGraph[Mass, N, DG] with MassConverter[N] {

      def links: Seq[(UnitOfMeasurement[Mass], UnitOfMeasurement[Mass], Bijection[N, N])] =
        List[(UnitOfMeasurement[Mass], UnitOfMeasurement[Mass], Bijection[N, N])](
          (tonne, megagram, BijectiveIdentity[N]),
          (milligram, gram, Scale10s(3)),
          (gram, kilogram, Scale10s(3)),
          (gram, megagram, Scale10s(6)),
          (tonne, kilotonne, Scale10s(3)),
          (tonne, megatonne, Scale10s(6)),
          (tonne, gigatonne, Scale10s(9)),
          (tonne, teratonne, Scale10s(12)),
          (tonne, petatonne, Scale10s(15)),
          (tonne, exatonne, Scale10s(18)),
          (tonne, zettatonne, Scale10s(21)),
          (tonne, yottatonne, Scale10s(24)),
          (kilogram, man, ScaleDouble(86.6)),
          (zettatonne, earth, ScaleDouble(5.9736)),
          (kilogram, sun, ScaleDouble(1.9891E30)),
          (yottatonne, jupiter, ScaleDouble(1.8986)),
          (zettatonne, saturn, ScaleDouble(568.46)),
          (zettatonne, neptune, ScaleDouble(102.43)),
          (zettatonne, uranus, ScaleDouble(86.810)),
          (zettatonne, venus, ScaleDouble(4.868)),
          (exatonne, mars, ScaleDouble(641.85)),
          (exatonne, mercury, ScaleDouble(330.22)),
          (exatonne, pluto, ScaleDouble(13.05)),
          (exatonne, moon, ScaleDouble(73.477)))

    }
}