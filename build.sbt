lazy val root = (project in file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
        name := "sortingAlgorithmVisualizer",
        scalaVersion := "3.2.2",
        Compile / scalaSource := baseDirectory.value / "src",
        Test / scalaSource := baseDirectory.value / "testImpl",
        scalaJSUseMainModuleInitializer := true,
        libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest-freespec" % "3.2.15" % "test",
            "org.scala-js" %%% "scalajs-dom" % "2.2.0",
            "com.raquo" %%% "laminar" % "15.0.0"
        )
    )