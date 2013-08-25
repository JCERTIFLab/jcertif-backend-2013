import sbt._
import Keys._
import play.Project._
import de.johoop.jacoco4sbt._
import JacocoPlugin._

object ApplicationBuild extends Build {

  val appName = "jcertif-backend"
  val appVersion = "0.3"

  lazy val s = Defaults.defaultSettings ++ Seq(jacoco.settings:_*)
  
  val appDependencies = Seq(
    "org.mongodb" % "mongo-java-driver" % "2.10.1",
    javaCore,"com.typesafe" %% "play-plugins-mailer" % "2.1.0",
    "commons-lang" % "commons-lang" % "2.4"
  )

  val main = play.Project(appName, appVersion, appDependencies, settings = s).settings(

    testOptions in Test ~= { args =>
    for {
        arg <- args
        val ta: Tests.Argument = arg.asInstanceOf[Tests.Argument]
        val newArg = if(ta.framework == Some(TestFrameworks.JUnit)) ta.copy(args = List.empty[String]) else ta
      } yield newArg
    },

    parallelExecution     in jacoco.Config := false,
    jacoco.reportFormats  in jacoco.Config := Seq(XMLReport("utf-8"), HTMLReport("utf-8")),
    jacoco.excludes       in jacoco.Config := Seq("views.*", "controllers.Reverse*", "controllers.javascript.*", "controllers.ref.*", "Routes*")
  )
}
