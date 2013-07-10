import sbt._
import Keys._

object MyBuild extends Build {

  lazy val project = Project("root", file(".")).settings(

    // basics
    name := "scala-benchmarking-template",
    organization := "com.example",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.10.0",

    // dependencies
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-jackson" % "3.2.4",
      "net.databinder.dispatch" %% "dispatch-core" % "0.10.1"
    ),
    resolvers += "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",

    // enable forking in run
    fork in run := true,

    // we need to add the runtime classpath as a "-cp" argument to the `javaOptions in run`, otherwise caliper
    // will not see the right classpath and die with a ConfigurationException
    javaOptions in run <++= (fullClasspath in Runtime) map { cp => Seq("-cp", sbt.Build.data(cp).mkString(":")) }
  )
}