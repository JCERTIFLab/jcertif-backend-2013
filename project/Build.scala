import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "jcertif-backend"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.mongodb" % "mongo-java-driver" % "2.10.1",
    javaCore
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  )

}
