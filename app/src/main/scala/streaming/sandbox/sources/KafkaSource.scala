package streaming.sandbox.sources

case class KafkaSource(bootstrapServers: String, topic: String, startingOffset: String)