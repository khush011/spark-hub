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

object Sql_json_Parquet {
  def main(args: Array[String]) = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Spark Word Count")
      .config("spark.driver.bindAddress", "127.0.0.1")
      .getOrCreate()
    val sc = spark.sparkContext

    spark.sparkContext.setLogLevel("ERROR")

  val df = spark.read.json("/home/xs218-khusin/binaries/spark-3.2.1-bin-hadoop3.2/bin/employee.json")
    //for converting to parquet
    //df.write.parquet("file_name.parquet")
    val df2 = spark.read.parquet("/home/xs218-khusin/binaries/spark-3.2.1-bin-hadoop3.2/bin/p_employee.parquet")
    val schema = List(StructField("_id", IntegerType,true),
      StructField("Product", StringType,true),
      StructField("Supplier_Name", StringType,true),
      StructField("Price", IntegerType,true),
      StructField("Department", StringType,true))

    val df3 = spark.read
      .option("header","true")
      .schema(StructType(schema))
      .csv("/home/xs218-khusin/binaries/spark-3.2.1-bin-hadoop3.2/bin/sample.csv")
//     val df_3 = spark.read.csv("/home/xs218-khusin/binaries/spark-3.2.1-bin-hadoop3.2/bin/sample.csv")
//    val header = df_3.first()
//    val df3 = df_3.filter(row=> row!= header)


    df.createOrReplaceTempView("dfsql")
    df3.createOrReplaceTempView("df3_sql")


    //displaying all records
    println(df3.printSchema())
    println("data set")
    val q = spark.sql("select * from df3_sql")
    println(q.show())


    println("products costlier than 5k")
    println(spark.sql("select _id, Product, Price from df3_sql where Price > 5000").show())


    println("fetching supplier names and product they deliver")
    println(spark.sql("select supplier_name, product from df3_sql").show())



  }
  }
