error id: file:///C:/Cognizant/Scala/FrontendPortal/build.sbt:`<none>`.
file:///C:/Cognizant/Scala/FrontendPortal/build.sbt
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 446
uri: file:///C:/Cognizant/Scala/FrontendPortal/build.sbt
text:
```scala
import sbtcrossproject.CrossPlugin.autoImport._
import scalajscrossproject.ScalaJSCrossPlugin.autoImport._
import sbtcrossproject.JVMPlatform
import org.scalajs.sbtplugin.ScalaJSPlugin

ThisBuild / scalaVersion := "3.3.1"

val http4sVersion = "0.23.23"
val circeVersion  = "0.14.6"

lazy val shared = crossProject(JVMPlatform, JSPlatform)
  .in(file("shared"))
  .settings(
    name := "shared",
    libraryDependencies += "com.liha@@oyi" %%% "upickle" % "3.1.0"
  )
  .jsConfigure(_.enablePlugins(ScalaJSPlugin))

lazy val frontend = project
  .in(file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(shared.js)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "com.raquo" %%% "laminar" % "0.14.2",
      "com.lihaoyi" %%% "upickle" % "3.1.0"
    )
  )

lazy val backend = project
  .in(file("backend"))
  .dependsOn(shared.jvm) // ✅ THIS IS CRITICAL!
  .settings(
    name := "backend",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl"           % http4sVersion,
      "org.http4s" %% "http4s-ember-server"  % http4sVersion,
      "org.http4s" %% "http4s-circe"         % http4sVersion,
      "io.circe"   %% "circe-generic"        % circeVersion,
      "com.comcast" %% "ip4s-core"           % "3.1.3"         // ✅ for ip4s if used
    )
  )

lazy val root = (project in file("."))
  .aggregate(frontend, backend, shared.js, shared.jvm)
  .settings(
    name := "frontendportal"
  )

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.