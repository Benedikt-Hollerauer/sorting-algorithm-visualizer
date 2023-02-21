lazy val root = project
    .in(file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
        name := "sortingAlgorithmVisualizer",
        scalaVersion := "3.2.2",
        Compile / scalaSource := baseDirectory.value / "src",
        Test / scalaSource := baseDirectory.value / "testImpl",
        libraryDependencies += "org.scalatest" %% "scalatest-freespec" % "3.2.15" % "test"
    )