name := "ssm-parser"

organization := "org.ssm.parser"

version := "1.0.0"

scalaVersion := "2.12.3"

scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.5" % "test",
  "junit" % "junit" % "4.12" % "test")

fork := true
