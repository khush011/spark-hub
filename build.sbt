ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion :="2.13.8"
libraryDependencies ++= Seq(
  //"org.apache.spark" %% "spark-core" % "1.6.1",
  "org.apache.spark" %% "spark-core" % "3.2.1",
  "org.apache.spark" %% "spark-sql" % "3.2.1" ,
  "org.apache.spark" %% "spark-hive" % "3.2.1"
)
lazy val root = (project in file("."))
  .settings(
    name := "Spark_word_count",
  )
//2.11.12
//import org.apache.spark.sql.SparkSession