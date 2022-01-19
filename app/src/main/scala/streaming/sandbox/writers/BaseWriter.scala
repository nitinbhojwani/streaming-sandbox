package streaming.sandbox.writers

import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.streaming.DataStreamWriter

trait BaseWriter {
  def write(dataframe: DataFrame): DataStreamWriter[Row]
}
