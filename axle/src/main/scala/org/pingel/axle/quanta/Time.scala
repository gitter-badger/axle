package org.pingel.axle.quanta

object Time extends Quantum {

  val wikipediaUrl = "http://en.wikipedia.org/wiki/Time"

  val second = UnitOfMeasurement(this, "second", "s")
  val hour = UnitOfMeasurement(this, "hour", "hr")
  val day = UnitOfMeasurement(this, "day", "d")
  val year = UnitOfMeasurement(this, "year", "yr")

  val unitsOfMeasurement = List(second, hour, day)

  val derivations = Nil
  
  val examples = List(
    Quantity(67.2, year, Some("2010 global average life expectancy"), Some("http://en.wikipedia.org/wiki/Life_expectancy")),
    Quantity(4540000000.0, year, Some("age of earth"), Some("http://en.wikipedia.org/wiki/Age_of_the_Earth"))
  )

}