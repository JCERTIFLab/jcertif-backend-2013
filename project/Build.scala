import sbt._
import Keys._
import PlayProject._
import com.github.play2war.plugin._

object ApplicationBuild extends Build {

  val appName = "jcertif-backend"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.mongodb" % "mongo-java-driver" % "2.10.1")

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
	    // ... Your own settings here
	    Play2WarKeys.servletVersion := "3.0"
   ).settings(Play2WarPlugin.play2WarSettings: _*)

}
