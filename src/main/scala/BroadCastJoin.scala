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

import org.apache.spark.sql.functions.broadcast

object BroadCastJoin {
    def main(args: Array[String]) = {

      val spark = SparkSession.builder()
        .master("local[*]")
        .appName("Spark Word Count")
        .config("spark.driver.bindAddress", "127.0.0.1")
        .getOrCreate()
      val sc = spark.sparkContext

      spark.sparkContext.setLogLevel("ERROR")

      val table1 = spark.range(1,100000)

      val rows = sc.parallelize(List(
        Row(1,"gold"),
        Row(2,"silver"),
        Row(3,"bronze")
      ))
      val rowSchema = StructType(Array(
        StructField("id", IntegerType),
        StructField("Medal", StringType)
      ))

      val table2 = spark.createDataFrame(rows, rowSchema)
      //in-efficient join
     // val join1 = table1.join(table2, "id")
     // println(join1.show())
      //broadcastJoin
      val b_join = table1.join(broadcast(table2), "id")
      b_join.show()
      //Thread.sleep(1000000)

    }
}
