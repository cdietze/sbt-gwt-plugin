import bintray.Keys._

import com.typesafe.sbt.SbtGit._

sbtPlugin := true

organization := "net.thunderklaus"

name := "sbt-gwt-plugin"

sbtVersion in Global := "0.13.0-RC1"

scalaVersion in Global := "2.10.4"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

publishMavenStyle := false

bintrayPublishSettings

repository in bintray := "sbt-plugins"

bintrayOrganization in bintray := None

versionWithGit

git.baseVersion := "1.1"

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.4.0")

seq(ScriptedPlugin.scriptedSettings: _*)
