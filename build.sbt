name := "scala-stats"

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
  "Typesafe Releases Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/"
)
    
libraryDependencies ++= Seq(
	//
    "com.typesafe"                % "config"              % "1.2.1",
    "org.scala-lang.modules"     %% "scala-async"         % "0.9.1",
	"com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
	//
	// test libs
	"org.specs2"        %% "specs2"  % "2.3.12" % "test",
	"com.typesafe.play" %% "play-test" % "2.2.0" % "test"
)
