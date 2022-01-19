build:
    ./gradlew build

# Helps to run a job locally. eg. `just run --reader KafkaReader --writer KafkaWriter`
run +PROGRAM_ARGS='':
    ./gradlew run --args="{{PROGRAM_ARGS}}"

kafka-consume TOPIC_NAME:
    docker exec --interactive --tty broker kafka-console-consumer --bootstrap-server broker:9092 --topic {{TOPIC_NAME}} --from-beginning

kafka-produce TOPIC_NAME:
    docker exec --interactive --tty broker kafka-console-producer --bootstrap-server broker:9092 --topic {{TOPIC_NAME}}

kafka:
    docker-compose -f ./develop/docker-compose.yaml up -d

kafka-stop:
    docker-compose -f ./develop/docker-compose.yaml down
