import AssemblyKeys._

name := "axle"

version := "0.1-SNAPSHOT"

organization := "org.pingel"

seq(assemblySettings: _*)

crossScalaVersions := Seq("2.9.1", "2.8.1")

resolvers += "array.ca" at "http://www.array.ca/nest-web/maven/" // for jblas

// curl http://cloud.github.com/downloads/mikiobraun/jblas/jblas-1.2.0.jar -o lib/jblas-1.2.0.jar
// mvn install:install-file -DgroupId=org.jblas -DartifactId=jblas \
//    -Dversion=1.2.0 -Dfile=jblas-1.2.0.jar -Dpackaging=jar -DgeneratePom=true

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

test in assembly := {}

// http://www.scala-sbt.org/using_sonatype.html

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) 
    Some("snapshots" at nexus + "content/repositories/snapshots") 
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>http://axle-lang.org</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:adampingel/axle.git</url>
    <connection>scm:git:git@github.com:adampingel/axle.git</connection>
  </scm>
  <developers>
    <developer>
      <id>pingel</id>
      <name>Adam Pingel</name>
      <url>http://pingel.org</url>
    </developer>
  </developers>)

// after 'publish', see:
//
// https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide#SonatypeOSSMavenRepositoryUsageGuide-8.ReleaseIt