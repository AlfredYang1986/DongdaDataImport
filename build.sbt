name := "Dongda-DataImporter"

version := "0.1"

scalaVersion := "2.11.8"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
    "com.pharbers" % "pharbers-module" % "0.1",
    "com.pharbers" % "pharbers-mongodb" % "0.1",
    "net.sourceforge.jexcelapi" % "jxl" % "2.6.12",
    "com.typesafe.play" %% "play-json" % "2.5.0-M2",
    "org.mongodb" % "casbah_2.11" % "3.1.1",
    "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
)