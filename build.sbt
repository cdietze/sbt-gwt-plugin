sbtPlugin := true

organization := "net.thunderklaus"

name := "sbt-gwt-plugin"

version := "1.0-SNAPSHOT"

resolvers += "Web plugin repo" at "http://siasia.github.com/maven2"

libraryDependencies <+= sbtVersion(v => "com.github.siasia" %% "xsbt-web-plugin" % ("0.1.0-" + v))

publishMavenStyle := true

seq(ScriptedPlugin.scriptedSettings: _*)