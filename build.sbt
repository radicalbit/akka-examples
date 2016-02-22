import com.typesafe.sbt.SbtScalariform._

organization := "io.radicalbit"

name := """akka-recipes"""

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "com.typesafe.akka" %% "akka-remote" % "2.4.0"
)

scalariformSettings

fork in run := true
