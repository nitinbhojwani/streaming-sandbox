package streaming.sandbox.stream

import org.apache.spark.sql.SparkSession

class GenericStream(sparkSession: SparkSession, streamConfig: StreamConfig) {
  def stream(): Unit = {
    val reader = streamConfig.reader
    val transformer = streamConfig.transformer
    val writer = streamConfig.writer

    writer.write(transformer.transform(reader.read(sparkSession)))
      .option("checkpointLocation", streamConfig.checkpointLocation)
      .start().awaitTermination()
  }
}
