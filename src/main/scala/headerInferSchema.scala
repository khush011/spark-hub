import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types._
import org.apache.spark.storage.StorageLevel
import scala.io.Source
import scala.collection.mutable.HashMap
import java.io.File
import org.apache.spark.sql.Row
import scala.collection.mutable.ListBuffer
import org.apache.spark.util.IntParam

object headerInferSchema {
  def main(args: Array[String]) = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Spark Word Count")
      .config("spark.driver.bindAddress", "127.0.0.1")
      .getOrCreate()
    val sc = spark.sparkContext

    spark.sparkContext.setLogLevel("ERROR")

    val myDF = spark.read
      .options(Map("inferSchema"->"false", "header"->"true"))
      .json("/home/xs218-khusin/binaries/spark-3.2.1-bin-hadoop3.2/bin/employee.json")
    //myDF.foreach(println)
    println("Inferschema False------------")
    println(myDF.printSchema)



    val schema = new StructType()
      .add("Id",IntegerType,true)
      .add("Name",StringType,true)
      .add("Age",IntegerType,true)
    //  .add("Salary",IntegerType, true)

//    val schema = List(StructField("Id",IntegerType,true),
//      StructField("Name",StringType,true),
//      StructField("Age",IntegerType,true))
//---------------------
    val myDataFrame = spark.read.option("header", "true")
      .schema(schema)
      .json("/home/xs218-khusin/binaries/spark-3.2.1-bin-hadoop3.2/bin/employee.json")
    //-------------------------------

    println(myDataFrame.show())
    println("Inferschema true--------")
    println(myDataFrame.printSchema)

  }
  }
//val schema = List(StructField("Id",IntegerType,true), StructField("Name",StringType,true),  StructField("Age",IntegerType,true))