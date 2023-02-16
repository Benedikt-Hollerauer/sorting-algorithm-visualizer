lazy val root = project
    .in(file("."))
    .enablePlugins(ScalaJSPlugin)
    .settings(
        name := "sortingAlgorithmVisualizer",
        scalaVersion := "3.2.2",
        Compile / scalaSource := baseDirectory.value / "src/main",
        Test / scalaSource := baseDirectory.value / "src/test",
        libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.15" % "test"
    )