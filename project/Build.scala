import sbt._
import Keys._
import play.Project._
import de.johoop.jacoco4sbt._
import JacocoPlugin._
import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys

object ApplicationBuild extends Build {

  val appName = "jcertif-backend"
  val appVersion = "0.1"

  lazy val s = Defaults.defaultSettings ++ Seq(jacoco.settings:_*)

  val webappDependencies = Seq(
    javaCore,"securesocial" %% "securesocial" % "master-SNAPSHOT"
  )
  
  val appDependencies = Seq(
    "org.mongodb" % "mongo-java-driver" % "2.10.1",
    javaCore,"com.typesafe" %% "play-plugins-mailer" % "2.1.0",
    "commons-lang" % "commons-lang" % "2.4",
    "commons-codec" % "commons-codec" % "1.3"
  )

  lazy val webapp = play.Project(appName + "-webapp", appVersion, webappDependencies, path = file("modules/webapp"), settings = s).settings(
    EclipseKeys.skipParents in ThisBuild := false,
    resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)
  )

  val main = play.Project(appName, appVersion, appDependencies, settings = s).settings(
    resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns),
    parallelExecution     in jacoco.Config := false,
    jacoco.reportFormats  in jacoco.Config := Seq(XMLReport("utf-8"), HTMLReport("utf-8")),
    jacoco.excludes       in jacoco.Config := Seq("views.*", "controllers.Reverse*", "controllers.javascript.*", "controllers.ref.*", "Routes*")
  )
}
