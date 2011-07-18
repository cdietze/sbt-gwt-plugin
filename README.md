Usage
====

Add the plugin to your project in `project/plugins/build.sbt`:

    resolvers += "GWT plugin repo" at "http://thunderklaus.github.com/maven"

    libraryDependencies += "net.thunderklaus" %% "sbt-gwt-plugin" % "1.1"

Add the GWT settings to your project in `build.sbt`:

    import net.thunderklaus.GwtPlugin._

    seq(gwtSettings :_*)

This adds the GWT compilation task, which can be called directly with `gwt-compile`. The GWT compilation is automatically triggered when the `package-war` task is invoked.

Development Mode
---

Use `gwt-devmode` to run your module in the GWT development mode shel. By default this runs the first module in the `gwtModules` list, but one can use the `gwt-set-module` command to instruct `gwt-devmode` to use the first module containing the substring passed to `gwt-set-module`.

Testing with Jetty
---

You can also test your `gwt-compile`d app with Jetty, by running `jetty-run` followed by `gwt-compile` and then browsing to `http://localhost:8080/`. Note that to use `jetty-run` you must add Jetty to your project dependencies like so:

    libraryDependencies ++= Seq(
      "org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty"
    )

Settings
---

The default GWT version is 2.3.0 which can be overridden like this

    gwtVersion := "2.2.0"

By default, all GWT modules in the source directory are compiled. You can specify which ones to build like this:

    gwtModules := List("net.thunderklaus.hello_gwt_sbt.Hello_gwt_sbt")

See also
---

The GWT settings include the web settings from the project [https://github.com/siasia/xsbt-web-plugin](). So, all tasks from that plugin are also available. Have a look at the documentation there.

License
---

sbt-gwt-plugin is open source software licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html). Feel free to use it!
