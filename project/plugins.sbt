// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies +=  "org.ow2.asm" % "asm-all" % "4.1" exclude("asm", "asm")

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.1.3")

addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.0.0")

