name := "sjs-test-error"

version := "0.1"

scalaVersion := "2.12.10"

import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}


val commonSettings = Seq(
  scalaVersion := "2.12.10",
  crossScalaVersions := Seq("2.12.10"),
  scalacOptions ++= Seq("-feature", "-deprecation", "-Xlint",  "-Ypartial-unification"),
  Compile / compile / scalacOptions  += "-Ywarn-unused-import",
  Compile / doc / scalacOptions += "-no-link-warnings"
)

val core = crossProject(JSPlatform, JVMPlatform)
  .in(file("core"))
  .settings(commonSettings)
  .settings(
    name := "reftree",
    libraryDependencies ++= Seq(
      "com.chuusai" %%% "shapeless" % "2.3.3",
      "com.lihaoyi" %%% "sourcecode" % "0.2.7",
      "com.lihaoyi" %%% "fastparse" % "2.3.0",
      "io.github.stanch" %%% "zipper" % "0.5.2",
      "com.softwaremill.quicklens" %%% "quicklens" % "1.4.8",
      "com.github.julien-truffaut" %%% "monocle-macro" % "2.0.0",
      "com.outr" %%% "scribe" % "2.7.9",
      "org.scalatest" %%% "scalatest" % "3.1.4" % Test,
      "org.scalacheck" %%% "scalacheck" % "1.14.3" % Test,
      "org.scalatestplus" %% "scalacheck-1-14" % "3.1.3.0" % Test
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-xml" % "1.2.0",
      "org.apache.xmlgraphics" % "batik-transcoder" % "1.14",
      "com.sksamuel.scrimage" % "scrimage-core" % "4.0.18",
      "de.sciss" %% "fingertree" % "1.5.5"
    )
  )
  .jsSettings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.1"
    ),
    jsDependencies ++= Seq(
      "org.webjars.npm" % "viz.js" % "1.7.0" / "1.7.0/viz.js"
    )
  )

lazy val coreJVM = core.jvm
lazy val coreJS = core.js

lazy val root = project.in(file("."))
  .aggregate(coreJVM, coreJS)
  .settings(commonSettings)
  .settings(
    publish := {},
    publishLocal := {},
    publishArtifact := false
  )