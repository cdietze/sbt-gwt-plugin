sbtPlugin := true

organization := "net.thunderklaus"

name := "sbt-gwt-plugin"

version := "1.0"

resolvers += "Web plugin repo" at "http://siasia.github.com/maven2"

libraryDependencies <+= sbtVersion(v => "com.github.siasia" %% "xsbt-web-plugin" % ("0.1.0-" + v))

seq(ScriptedPlugin.scriptedSettings: _*)

publishMavenStyle := true

publishTo := Some(Resolver.file("Local", Path.userHome / "thunderklaus.github.com" / "maven" asFile)(Patterns(true, Resolver.mavenStyleBasePattern)))
