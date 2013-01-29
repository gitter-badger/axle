
name := "axle-languages"

version := "0.1-SNAPSHOT"

// version := "0.1-M5"

organization := "org.pingel"

crossScalaVersions := Seq("2.9.1")

initialCommands in console := "import axle._; import axle.stats._; import axle.quanta._; import axle.graph._; import axle.matrix._; import axle.ml._; import axle.visualize._; import collection._"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
  "org.pingel" %% "axle" % "0.1-SNAPSHOT",
  "net.liftweb" % "lift-json_2.9.0-1" % "2.4",
  "net.liftweb" % "lift-common_2.9.0-1" % "2.4",
  "org.specs2" %% "specs2" % "1.11" % "test"
)