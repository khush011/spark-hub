import org.apache.spark.sql.SparkSession
object PartitionandCoalesce {
  def main(args: Array[String]) = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Spark Word Count")
      .config("spark.driver.bindAddress", "127.0.0.1")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    val sc = spark.sparkContext
    val rdd = sc.parallelize(1 to 100)
    println("Partitioning Size of RDD: "+rdd.partitions.size)
    //println(rdd.partitions.foreach(x=>x.toString))
    rdd.saveAsTextFile("/home/xs218-khusin/binaries/partition")


    val repart_rdd = rdd.repartition(10)
    println("Partitioning Size of repart_RDD: "+ repart_rdd. partitions.size)
    //repart_rdd.partitions.foreach(x=> println(x.toString))
    repart_rdd.saveAsTextFile("/home/xs218-khusin/binaries/repart")
  }

}