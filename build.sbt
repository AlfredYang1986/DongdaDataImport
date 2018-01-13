name := "Dongda-DataImporter"

version := "0.1"

scalaVersion := "2.11.8"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
    "com.pharbers" % "pharbers-modules" % "0.2",
    "com.pharbers" % "pharbers-cli-traits" % "0.2",
    "com.pharbers" % "auth-token" % "0.2",
    "com.pharbers" % "encrypt" % "0.2",
    "com.pharbers" % "mongodb-connect" % "0.2",
    "com.pharbers" % "mongodb-driver" % "0.2",
    "net.sourceforge.jexcelapi" % "jxl" % "2.6.12",
    "com.typesafe.play" %% "play-json" % "2.5.0-M2",
    "org.mongodb.scala" %% "mongo-scala-driver" % "1.2.1",
    "org.mongodb.spark" %% "mongo-spark-connector" % "2.0.0",
    "org.mongodb" % "casbah_2.11" % "3.1.1",
    "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
)