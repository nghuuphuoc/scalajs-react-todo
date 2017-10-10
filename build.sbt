name := "todo"
version := "0.1"
scalaVersion := "2.12.3"
enablePlugins(ScalaJSPlugin)
// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "com.github.japgolly.scalajs-react" %%% "core" % "1.1.0",
  "com.github.japgolly.scalajs-react" %%% "extra" % "1.1.0"
)

skip in packageJSDependencies := false

jsDependencies ++= Seq(
  "org.webjars.bower" % "react" % "15.6.1"
    /        "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "15.6.1"
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM",

  "org.webjars.bower" % "react" % "15.6.1"
    /         "react-dom-server.js"
    minified  "react-dom-server.min.js"
    dependsOn "react-dom.js"
    commonJSName "ReactDOMServer",

  ProvidedJS / "vendor/js/why-did-you-update/why-did-you-update-0.0.8.js"
    minified "vendor/js/why-did-you-update/why-did-you-update-0.0.8.min.js"
    dependsOn "react-with-addons.js"
    dependsOn "react-dom.js"
)
