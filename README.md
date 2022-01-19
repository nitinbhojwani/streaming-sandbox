### Dev Setup
1. Install just cli using https://github.com/casey/just

2. `just build`

3. Run Kafka locally using: `just kafka`

4. Create topic in Kafka: `just kafka-topic <topic-name>`

5. Connect to a Kafka Topic as producer: `just kafka-produce <topic-name>`

6. Connect to a Kafka Topic as consumer: `just kafka-consume <topic-name>`

7. Run stream by providing reader, input-source detail, transformer, writer and output-sink configuration parameters
<br/>e.g.
```
just run --reader=KafkaReader --writer=KafkaWriter --transformer=DummyTransformer \
--readerArgs="bootstrapServers=localhost:9092,topic=input-topic,startingOffset=earliest" \
--writerArgs="bootstrapServers=localhost:9092,topic=output-topic" --checkpointLocation /tmp/1
```

### References
1. Run Kafka Locally:<br/>
   https://developer.confluent.io/quickstart/kafka-docker/
   
2. Spark Streaming and Kafka:<br/>
    https://spark.apache.org/docs/2.2.0/streaming-kafka-0-8-integration.html

3. 