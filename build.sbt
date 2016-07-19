name := """ExploreDalmiya"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalatestplus" % "play_2.11" % "1.4.0" % "test",
  "org.mockito" % "mockito-core" % "1.10.19" % "test",
  "org.pegdown" % "pegdown" % "1.6.0",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.13"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

testOptions := Seq(Tests.Argument(TestFrameworks.ScalaTest, "-o", "-h", "target/test-reports"))

//sample comment
