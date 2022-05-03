import org.apache.spark.sql.SparkSession

object SparkWordCount {
  def main(args:Array[String]) = {

  val spark = SparkSession.builder()
    .master("local[*]")
    .appName("Spark Word Count")
    .config("spark.driver.bindAddress", "127.0.0.1")
    .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")


  val sc = spark.sparkContext

  val lines = sc.parallelize(
    Seq("Hello world I am here",
      "Hello Science",
      "Data Science test three",
      "I am Data Ops Intern",
    "Data is hard for Intern",
    "world is full of Data "))

  val count = lines.flatMap(line => line.split(" "))
    .map(word => (word, 1)).reduceByKey(_ + _)
     // count.collect()
  count.foreach(print)
    println("")


}
}
