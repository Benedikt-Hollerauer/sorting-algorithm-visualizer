ThisBuild / name := "sortingAlgorithmVisualizer"
ThisBuild / scalaVersion := "3.2.2"

lazy val root = project
    .in(file("."))
    .enablePlugins(ScalaJSPlugin)
    .aggregate(
        core,
        testImpl,
        presentation
    ).settings(
        scalaJSUseMainModuleInitializer := true
    )

lazy val core = project
    .in(file("core"))
    .settings(
        Compile / scalaSource := baseDirectory.value
    )

lazy val testImpl = project
    .in(file("testImpl"))
    .dependsOn(core)
    .settings(
        Test / scalaSource := baseDirectory.value,
        libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest-freespec" % "3.2.15" % "test"
        )
    )

lazy val presentation = project
    .in(file("presentation"))
    .dependsOn(core)
    .settings(
        Compile / scalaSource := baseDirectory.value,
        libraryDependencies ++= Seq(
            "org.scala-js" %%% "scalajs-dom" % "2.2.0",
            "com.raquo" %%% "laminar" % "15.0.0"
        )
    )