package streaming.sandbox.commons

import org.apache.spark.sql.SparkSession

object SparkSessionProvider {
  lazy val spark = SparkSession
    .builder()
    .config("spark.master", "local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  def getSparkSession(): SparkSession = spark
}

