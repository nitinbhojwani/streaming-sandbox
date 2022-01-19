package streaming.sandbox.writers

import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.streaming.DataStreamWriter
import streaming.sandbox.sinks.KafkaSink


class KafkaWriter(kafkaSink: KafkaSink) extends BaseWriter {
  def write(dataframe: DataFrame): DataStreamWriter[Row] = {
    dataframe
      .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaSink.bootstrapServers)
      .option("topic", kafkaSink.topic)
  }
}
