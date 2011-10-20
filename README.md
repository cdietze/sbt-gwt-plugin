Usage
====

Add the plugin to your project in `project/plugins.sbt`:

    resolvers += "GWT plugin repo" at "http://thunderklaus.github.com/maven"

    resolvers += "Web plugin repo" at "http://siasia.github.com/maven2"

    addSbtPlugin( "com.github.siasia" % "xsbt-web-plugin" % "0.1.2")

    addSbtPlugin("net.thunderklaus" % "sbt-gwt-plugin" % "1.1-SNAPSHOT")

Add the GWT settings to your project in `build.sbt`:

    import net.thunderklaus.GwtPlugin._

    seq(gwtSettings :_*)

This adds the GWT compilation task, which can be called directly with `gwt-compile`. The GWT compilation is automatically triggered when the `package-war` task is invoked.

Development Mode
---

Use `gwt-devmode` to run your module in the GWT development mode shell. By default this runs the first module in the `gwtModules` list, but one can use the `gwt-set-module` command to instruct `gwt-devmode` to use the first module containing the substring passed to `gwt-set-module`.

Development Mode with Google App Engine
---

It is possible to run the Google App Engine development server when running the GWT devmode shell. You must simply configure `gaeSdkPath`:

    gaeSdkPath := Some("/path/to/appengine-java-sdk-x.x.x")

If you prefer not to hardcode paths in your SBT build, you can easily extract the path from a shell environment variable like so:

    gaeSdkPath := Option(System.getenv("APPENGINE_SDK_HOME"))

Enter the following in the SBT console prior to invoking `gwt-devmode` to see the exact configuration changes made when running the GWT devmode shell with the Google App Engine dev server:

    set logLevel := Level.Debug

Testing with Jetty
---

You can also test your `gwt-compile`d app with Jetty, by running `jetty-run` followed by `gwt-compile` and then browsing to `http://localhost:8080/`. Note that to use `jetty-run` you must add Jetty to your project dependencies like so:

    libraryDependencies ++= Seq(
      "org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty"
    )

Settings
---

The default GWT version is 2.3.0 which can be overridden like this:

    gwtVersion := "2.2.0"

By default, all GWT modules in the source directory are compiled. You can specify which ones to build like this:

    gwtModules := List("net.thunderklaus.hello_gwt_sbt.Hello_gwt_sbt")

Additional arguments can be passed to the JVM used to run GWT devmode and the GWT compiler:

    javaOptions in Gwt += "-mx512M"

See also
---

The GWT settings include the web settings from the project [https://github.com/siasia/xsbt-web-plugin](). So, all tasks from that plugin are also available. Have a look at the documentation there.

License
---

sbt-gwt-plugin is open source software licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html). Feel free to use it!
