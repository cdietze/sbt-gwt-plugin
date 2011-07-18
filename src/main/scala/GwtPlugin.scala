package net.thunderklaus

import sbt._
import sbt.Keys._
import java.io.File
import com.github.siasia.WebPlugin._

object GwtPlugin extends Plugin {

  lazy val Gwt = config("gwt") extend (Compile)

  val gwtModules = TaskKey[Seq[String]]("gwt-modules")
  val gwtCompile = TaskKey[Unit]("gwt-compile", "Runs the GWT compiler")
  val gwtDevMode = TaskKey[Unit]("gwt-devmode", "Runs the GWT devmode shell")
  val gwtVersion = SettingKey[String]("gwt-version")

  var gwtModule: Option[String] = None
  val gwtSetModule = Command.single("gwt-set-module") { (state, arg) =>
    Project.evaluateTask(gwtModules, state) match {
      case Some(Value(mods)) => {
        gwtModule = mods.find(_.toLowerCase.contains(arg))
        gwtModule match {
          case Some(m) => println("gwt-devmode will run: " + m)
          case None => println("No match for '" + arg + "' in " + mods.mkString(", "))
        }
      }
      case _ => None
    }
    state
  }

  lazy val gwtSettings: Seq[Setting[_]] = webSettings ++ gwtOnlySettings

  lazy val gwtOnlySettings: Seq[Setting[_]] = inConfig(Gwt)(Defaults.configSettings) ++ Seq(
    managedClasspath in Gwt <<= (managedClasspath in Compile, update) map {
      (cp, up) => cp ++ Classpaths.managedJars(Provided, Set("src"), up)
    },
    unmanagedClasspath in Gwt <<= (unmanagedClasspath in Compile).identity,
    autoScalaLibrary := false,
    gwtVersion := "2.3.0",
    libraryDependencies <++= gwtVersion(gwtVersion => Seq(
      "com.google.gwt" % "gwt-user" % gwtVersion % "provided",
      "com.google.gwt" % "gwt-dev" % gwtVersion % "provided",
      "javax.validation" % "validation-api" % "1.0.0.GA" % "provided" withSources (),
      "com.google.gwt" % "gwt-servlet" % gwtVersion)),
    gwtModules <<= (javaSource in Compile) map { javaSource => findGwtModules(javaSource) },

    gwtDevMode <<= (dependencyClasspath in Gwt, javaSource in Compile,
                    gwtModules, temporaryWarPath, streams) map {
      (dependencyClasspath, javaSource, gwtModules, warPath, s) => {
        val module = gwtModule.getOrElse(gwtModules.head)
        val cp = dependencyClasspath.map(_.data.absolutePath) :+ javaSource.absolutePath
        val command = mkGwtCommand(cp, "com.google.gwt.dev.DevMode", warPath, module)
        s.log.info("Running GWT devmode on: " + module)
        s.log.debug("Running GWT devmode command: " + command)
        command !
      }
    },
    gwtDevMode <<= gwtDevMode.dependsOn(prepareWebapp),

    gwtCompile <<= (dependencyClasspath in Gwt, javaSource in Compile,
                    gwtModules, temporaryWarPath, streams) map {
      (dependencyClasspath, javaSource, gwtModules, warPath, s) => {
        val cp = dependencyClasspath.map(_.data.absolutePath) :+ javaSource.absolutePath
        val command = mkGwtCommand(
          cp, "com.google.gwt.dev.Compiler", warPath, gwtModules.mkString(" "))
        s.log.info("Compiling GWT modules: " + gwtModules.mkString(","))
        s.log.debug("Running GWT compiler command: " + command)
        command !
      }
    },
    gwtCompile <<= gwtCompile.dependsOn(prepareWebapp),
    packageWar <<= packageWar.dependsOn(gwtCompile),

    commands ++= Seq(gwtSetModule)
  )

  private def mkGwtCommand(cp: Seq[File], clazz: String, warPath: File, args: String) =
    val command = "java -cp " + cp.mkString(File.pathSeparator) + " " + clazz +
      " -war " + warPath.absolutePath + " " + args

  private def findGwtModules(srcRoot: File): Seq[String] = {
    import Path.relativeTo
    val files = (srcRoot ** "*.gwt.xml").get
    val relativeStrings = files.flatMap(_ x relativeTo(srcRoot)).map(_._2)
    val modules = relativeStrings.map(_.dropRight(".gwt.xml".length).replace(File.separator, "."))
    modules
  }
}
