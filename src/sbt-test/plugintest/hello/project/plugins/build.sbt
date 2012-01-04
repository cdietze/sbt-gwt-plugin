
resolvers += "GWT plugin repo" at "http://thunderklaus.github.com/maven"

resolvers += Resolver.file("Local Maven Repository", Path.userHome / "thunderklaus.github.com" / "maven" asFile)(Patterns(true, Resolver.mavenStyleBasePattern))

addSbtPlugin("net.thunderklaus" % "sbt-gwt-plugin" % "1.1-SNAPSHOT")
 
