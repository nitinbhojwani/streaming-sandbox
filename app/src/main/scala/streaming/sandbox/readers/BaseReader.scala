package streaming.sandbox.readers

import org.apache.spark.sql.{DataFrame, SparkSession}

trait BaseReader {
  def read(sparkSession: SparkSession): DataFrame
}