ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "marsRover"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test
libraryDependencies += "org.scalatestplus" %% "mockito-5-12" % "3.2.19.0" % "test"
