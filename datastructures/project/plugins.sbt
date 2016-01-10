resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// The Play plugin
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0-RC2")

// Code Coverage
addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")

// Dependency graphs
addSbtPlugin("com.gilt" % "sbt-dependency-graph-sugar" % "0.7.5-1")
//addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.5")

// Find updates available for all your dependencies
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.9")

// ScalaStyle 
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"
