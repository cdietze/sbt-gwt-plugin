sbtPlugin := true

organization := "net.thunderklaus"

name := "sbt-gwt-plugin"

version := "1.1-SNAPSHOT"

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.4.0")

seq(ScriptedPlugin.scriptedSettings: _*)

publishMavenStyle := true

publishTo := Some(Resolver.file("Local", Path.userHome / "thunderklaus.github.com" / "maven" asFile)(Patterns(true, Resolver.mavenStyleBasePattern)))
