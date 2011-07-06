package net.thunderklaus

import sbt._
import sbt.Keys._
import com.github.siasia.WebPlugin._

object GwtPlugin extends Plugin {

  lazy val Gwt = config("gwt") extend (Compile)

  val gwtModules = TaskKey[Seq[String]]("gwt-modules")
  val gwtTemporaryPath = SettingKey[File]("gwt-temporary-path")
  val gwtCompile = TaskKey[Unit]("gwt-compile", "Runs the GWT compiler")

  lazy val gwtSettings: Seq[Setting[_]] = webSettings ++ inConfig(Gwt)(Defaults.configSettings) ++ Seq(
    managedClasspath in Gwt <<= (managedClasspath in Compile, update) map ((cp, up) => cp ++ Classpaths.managedJars(Provided, Set("src"), up)),
    autoScalaLibrary := false,
    libraryDependencies ++= Seq(
      "com.google.gwt" % "gwt-user" % "2.3.0" % "provided",
      "com.google.gwt" % "gwt-dev" % "2.3.0" % "provided",
      "javax.validation" % "validation-api" % "1.0.0.GA" % "provided" withSources (),
      "com.google.gwt" % "gwt-servlet" % "2.3.0"),
    gwtModules <<= (javaSource in Compile) map (javaSource => findModules(javaSource)),
    gwtTemporaryPath <<= (target) { (target) => target / "gwt" },
    gwtCompile <<= (dependencyClasspath in Gwt, javaSource in Compile, gwtModules, gwtTemporaryPath, streams) map
      { (dependencyClasspath, javaSource, gwtModules, tempPath, s) =>
        {
          IO.createDirectory(tempPath)
          val command = "java -cp " + dependencyClasspath.map(_.data.absolutePath).mkString(";") + ";" + javaSource.absolutePath + " com.google.gwt.dev.Compiler -war " + tempPath.absolutePath + " " + gwtModules.mkString(" ")
          s.log.info("Compiling " + gwtModules.length + " GWT module" + { if (gwtModules.length == 1) "" else "s" } + ": " + gwtModules.mkString(","))
          s.log.debug("Running GWT compiler command: " + command)
          command !
        }
      },
    prepareWebapp <<= prepareWebapp.dependsOn(gwtCompile),
    webappResources <<= (webappResources, gwtTemporaryPath) { (w: PathFinder, g: File) => w +++ PathFinder(g) })

  private def findModules(srcRoot: File): Seq[String] = {
    import Path.relativeTo
    val files = (srcRoot ** "*.gwt.xml").get
    val relativeStrings = files.flatMap(_ x relativeTo(srcRoot)).map(_._2)
    val modules = relativeStrings.map(_.dropRight(".gwt.xml".length).replaceAll("\\\\", "."))
    modules
  }
}
