package streaming.sandbox.readers

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SparkSession
import streaming.sandbox.sources.KafkaSource


class KafkaReader(kafkaSource: KafkaSource) extends BaseReader{
  override def read(spark: SparkSession): DataFrame = {
    val streamingDf = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaSource.bootstrapServers)
      .option("subscribe", kafkaSource.topic)
      .option("startingOffsets", kafkaSource.startingOffset) // From starting
      .load()

    streamingDf
  }
}
