ThisBuild / scalaVersion := "3.3.1"
ThisBuild / scalacOptions += "-Xfatal-warnings"

lazy val sortingAlgorithmVisualizer: Project = project
    .in(file("."))
    .aggregate(
        core,
        testImpl,
        presentation
    )

lazy val core: Project = project
    .in(file("core"))
    .enablePlugins(ScalaJSPlugin)
    .settings(
        Compile / scalaSource := baseDirectory.value
    )

lazy val testImpl: Project = project
    .in(file("testImpl"))
    .enablePlugins(ScalaJSPlugin)
    .dependsOn(core)
    .settings(
        Test / scalaSource := baseDirectory.value,
        libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest-freespec" % "3.2.17" % "test"
        )
    )

lazy val presentation: Project = project
    .in(file("presentation"))
    .enablePlugins(ScalaJSPlugin)
    .dependsOn(core)
    .settings(
        Compile / scalaSource := baseDirectory.value,
        scalaJSUseMainModuleInitializer := true,
        libraryDependencies ++= Seq(
            "org.scala-js" %%% "scalajs-dom" % "2.2.0",
            "com.raquo" %%% "laminar" % "16.0.0"
        )
    )