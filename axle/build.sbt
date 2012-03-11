import AssemblyKeys._

name := "axle"

version := "1.0"

organization := "org.pingel"

seq(assemblySettings: _*)

// scalaVersion := "2.9.1"
crossScalaVersions := Seq("2.9.1") // "2.8.1"

test in assembly := {}

libraryDependencies ++= Seq(
  // "jung" % "jung" % "1.7.6",
  "net.sf.jung" % "jung-algorithms" % "2.0.1",
  "net.sf.jung" % "jung-api" % "2.0.1",
  "net.sf.jung" % "jung-graph-impl" % "2.0.1",
  "net.sf.jung" % "jung-io" % "2.0.1",
  "net.sf.jung" % "jung-visualization" % "2.0.1",
  "org" % "jblas" % "1.2.0",
  "org.specs2" %% "specs2" % "1.8.1" % "test"
)

resolvers ++= Seq(
  "array.ca" at "http://www.array.ca/nest-web/maven/"
)

// That array.ca seems to host jblas, but if that goes away, try:

// curl http://cloud.github.com/downloads/mikiobraun/jblas/jblas-1.2.0.jar -o lib/jblas-1.2.0.jar

// Or with maven:
// curl http://cloud.github.com/downloads/mikiobraun/jblas/jblas-1.2.0.jar -o jblas-1.2.0.jar
// mvn install:install-file -DgroupId=org.jblas -DartifactId=jblas -Dversion=1.2.0 -Dfile=jblas-1.2.0.jar -Dpackaging=jar -DgeneratePom=true


