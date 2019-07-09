
name := "SlickCreateTableBug"

version := "0.1"

scalaVersion := "2.12.8"

lazy val hello = (project in file("."))
  .settings(
    name := "hello",
    libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.0",
    libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",
    libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"
)
