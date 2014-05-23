name := "scala-stats"

version := "1.0-SNAPSHOT"

//resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
    "com.typesafe" % "config" % "1.2.1",
    "org.scala-lang.modules" %% "scala-async" % "0.9.1",
	"com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2"
)
