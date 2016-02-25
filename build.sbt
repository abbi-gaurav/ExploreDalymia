name := """ExploreDalmiya"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus" % "play_2.11" % "1.4.0",
  "org.pegdown" % "pegdown" % "1.6.0",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.9"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

testOptions:= Seq(Tests.Argument(TestFrameworks.ScalaTest,"-o", "-h", "target/test-reports"))

maintainer := "Gaurav Abbi"

dockerBaseImage := "heroku/java"