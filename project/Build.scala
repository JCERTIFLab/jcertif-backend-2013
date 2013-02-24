import sbt._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "jcertif-backend"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.mongodb" % "mongo-java-driver" % "2.10.1"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    // Add your own project settings here
  )

}
