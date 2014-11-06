resolvers += Classpaths.typesafeResolver

libraryDependencies <+= sbtVersion("org.scala-sbt" % "scripted-plugin" % _)

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")