name := """dtapp"""


//lazy val root = (project in file(".")).enablePlugins(PlayJava)

//scalaVersion := "2.11.1"


lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean, PlayEnhancer)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  //javaEbean,
  cache,
  "org.postgresql" % "postgresql" % "9.2-1003-jdbc4",
  javaWs
)
