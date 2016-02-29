import com.typesafe.sbt.SbtScalariform._

organization := "io.radicalbit"

name := """akka-recipes"""

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "com.typesafe.akka" % "akka-slf4j_2.11" % "2.4.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3"
)

scalariformSettings

fork in run := true
