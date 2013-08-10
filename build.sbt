sbtPlugin := true

organization := "net.thunderklaus"

name := "sbt-gwt-plugin"

version := "1.1-SNAPSHOT"

// sbtVersion in Global := "0.13.0-RC1"

scalaVersion in Global := "2.10.2"

crossScalaVersions := Seq("2.10.1", "2.10.2")

crossScalaVersions ++= Seq("2.10.0")

crossScalaVersions ++= Seq("2.9.0", "2.9.1", "2.9.2", "2.9.3")

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.4.0")

seq(ScriptedPlugin.scriptedSettings: _*)

publishMavenStyle := true

publishTo := Some(Resolver.file("Local", Path.userHome / "thunderklaus.github.com" / "maven" asFile)(Patterns(true, Resolver.mavenStyleBasePattern)))
