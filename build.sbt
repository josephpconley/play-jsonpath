name := "play-jsonpath"

organization := "com.josephpconley"

version := "1.0"

scalaVersion := "2.11.4"

crossScalaVersions := Seq("2.10.4", "2.11.4")

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
	"com.typesafe.play" %% "play-json" % "[2.2.0,)",
	"io.gatling" %% "jsonpath" % "[0.4.0,)",
	"com.typesafe.play" %% "play-test" % "[2.2.0,)" % "test"
)

publishMavenStyle := true

publishArtifact in Test := false

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/josephpconley/play-jsonpath"))

pomExtra := (
    <scm>
      <url>git@github.com:josephpconley/play-jsonpath.git</url>
      <connection>scm:git:git@github.com:josephpconley/play-jsonpath.git</connection>
    </scm>
    <developers>
      <developer>
        <id>josephpconley</id>
        <name>Joe Conley</name>
        <url>http://www.josephpconley.com</url>
      </developer>
    </developers>)

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