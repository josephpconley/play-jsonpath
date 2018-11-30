name := "play-jsonpath"

organization := "com.josephpconley"

version := "2.7.0-SNAPSHOT"

scalaVersion := "2.12.7"

crossScalaVersions := Seq("2.10.4", "2.11.4", "2.12.7")

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "[2.6.0,2.6.+)",
  "io.gatling" %% "jsonpath" % "0.6.8",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

publishMavenStyle := true

publishArtifact in Test := false

pgpPublicRing := file("/home/jconley/.gnupg/pubring.gpg")
pgpSecretRing := file("/home/jconley/.gnupg/secring.gpg")

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
