name := """unq-persistencia-aterrizar"""

version := "1.0"

scalaVersion := "2.10.3"

//mainClass in Compile := Some("HelloSlick")

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "2.0.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.3.170",
  "org.scalatest" %% "scalatest" % "2.0",
  "com.github.nscala-time" %% "nscala-time" % "1.4.0",
  "joda-time" % "joda-time-hibernate" % "1.3",
  "org.hibernate" % "hibernate-core" % "4.3.6.Final",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "org.hsqldb" % "hsqldb" % "2.2.8",
  "org.slf4j" % "slf4j-api" % "1.6.1",
  "org.slf4j" % "slf4j-log4j12" % "1.6.1",
  "log4j" % "log4j" % "1.2.16"
)
