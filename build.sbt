name := "play-jsonpath"

organization := "com.josephconley"

version := "1.0"

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
	"com.typesafe.play" %% "play" % "[2.2.0,)",
	"io.gatling" % "jsonpath_2.10" % "0.4.0",
	"com.typesafe.play" %% "play-test" % "[2.2.0,)" % "test"
)

//lazy val sitePath = settingKey[File]("Path to the publishing site")
//
//sitePath := Path.userHome / "sites" / "julienrf.github.com"
//
//publishTo := {
//  Some(Resolver.file("Github Pages", sitePath.value / (if (version.value.trim.endsWith("SNAPSHOT")) "repo-snapshots" else "repo") asFile))
//}
//
//lazy val publishDoc = taskKey[Unit]("Publish the documentation")
//
//publish := {
//  publish.value
//  IO.copyDirectory((doc in Compile).value, sitePath.value / "play-jsonp-filter" / version.value / "api")
//}