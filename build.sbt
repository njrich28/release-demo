name := """release-demo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)


// version properties

val gitHeadCommitSha = taskKey[String]("Determines the current git commit SHA")

gitHeadCommitSha := Process("git rev-parse HEAD").lines.head

val makeVersionProperties = taskKey[Seq[File]]("Makes a version.properties file.")

makeVersionProperties := {
  val propFile = (resourceManaged in Compile).value / "version.properties"
  val content = "commitSha=%s\nversion=%s" format (gitHeadCommitSha.value, version.value)
  IO.write(propFile, content)
  Seq(propFile)
}

resourceGenerators in Compile <+= makeVersionProperties

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "io.swagger" %% "swagger-play2" % "1.5.3.2",
//  "org.webjars.npm" % "swagger-ui" % "2.1.4",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
