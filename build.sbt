name := """i18n-compilation-safety-with-macro"""
organization := "givers.i18n.example"

version := "1.0-SNAPSHOT"

lazy val macros = (project in file("macros"))
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "com.typesafe.play" %% "play" % "2.6.13",
    )
  )

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .aggregate(macros)
  .dependsOn(macros)

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  guice
)
