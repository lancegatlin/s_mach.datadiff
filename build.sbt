val publishSettings = Seq(
  publishMavenStyle := true,
  pomIncludeRepository := { _ => false },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra :=
    <url>https://github.com/S-Mach/s_mach.datadiff</url>
      <licenses>
        <license>
          <name>MIT</name>
          <url>http://opensource.org/licenses/MIT</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:S-Mach/s_mach.datadiff.git</url>
        <connection>scm:git:git@github.com:S-Mach/s_mach.datadiff.git</connection>
        <developerConnection>scm:git:git@github.com:S-Mach/s_mach.datadiff.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <id>lancegatlin</id>
          <name>Lance Gatlin</name>
          <email>lance.gatlin@gmail.com</email>
          <organization>S-Mach</organization>
          <organizationUrl>http://s-mach.net</organizationUrl>
        </developer>
      </developers>
)

val defaultSettings = Defaults.coreDefaultSettings ++ publishSettings ++ Seq(
  scalaVersion := "2.11.1",
  organization := "net.s_mach",
  version := "0.1-SNAPSHOT",
  scalacOptions ++= Seq(
    "-feature",
    "-unchecked",
    "-deprecation"
  )
)

val test = Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test"
)

lazy val dataDiff = Project(
  id = "datadiff",
  base = file("."),
  aggregate = Seq(
    dataDiffCore,
    dataDiffMacro
  ),
  dependencies = Seq("datadiff-macro")
)
  .settings(defaultSettings: _*)
  .settings(libraryDependencies ++= Seq(
  "com.googlecode.java-diff-utils" % "diffutils" % "1.3.0"
) ++test)

lazy val dataDiffCore = Project(
  id = "datadiff-core",
  base = file("datadiff-core")
)
  .settings(defaultSettings: _*)

lazy val dataDiffMacro = Project(
  id = "datadiff-macro",
  base = file("datadiff-macro"),
  dependencies = Seq("datadiff-core")
)
  .settings(defaultSettings: _*)
  .settings(libraryDependencies ++= Seq(
    "net.s_mach" %% "codetools" % "0.1-SNAPSHOT"
  ))

