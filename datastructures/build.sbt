organization := "com.github.twistedpair.lustbox.datastructures"

name := "data-structures"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xlint")

libraryDependencies ++= Seq(

  "joda-time" % "joda-time" % "1.6.2",
  "org.psjava" % "psjava" % "0.1.19",

  // Test
  "org.scalatest" %% "scalatest" % "2.2.5" % "test",
  "junit" % "junit" % "4.12" % "test"

)

// Alternative paths
scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala"

scalaSource in Test := baseDirectory.value / "src" / "test" / "scala"


// don't waste space/time compiling Docs
sources in doc in Compile := List()

initialCommands in console := "import com.github.twistedpair.lustbox.datastructures._"

// Eclipse plugin config
// always download and attach sources
EclipseKeys.withSource := true
