Usage
====

Add the plugin to your project in `project/plugins/build.sbt`:

    resolvers += "GWT plugin repo" at "http://thunderklaus.github.com/maven"

    libraryDependencies += "net.thunderklaus" %% "sbt-gwt-plugin" % "1.0"

Add the GWT settings to your project in `build.sbt`:

    import net.thunderklaus.GwtPlugin._

    seq(gwtSettings :_*)

This adds the GWT compilation task, which can be called directly with `gwt-compile`. The GWT compilation is automatically triggered when the `prepare-webapp` or `package-war` tasks are built.

Settings
---

The default GWT version is 2.3.0 which can be overridden like this

```
gwtVersion := "2.2.0"
```

By default, all GWT modules in the source directory are compiled. You can specify which ones to build like this:

```
gwtModules := List("net.thunderklaus.hello_gwt_sbt.Hello_gwt_sbt")
```

See also
---

The GWT settings contain the web settings from the project [https://github.com/siasia/xsbt-web-plugin](). So, all tasks from that plugin are also available. Have a look at the documentation there.