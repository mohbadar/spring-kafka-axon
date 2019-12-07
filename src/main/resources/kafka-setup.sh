

cd /home/nsia/Desktop/tools/confluent-5.2.2/bin/

kafka-topics --create --zookeeper localhost:2181 --topic events --partitions 1 --replication-factor 1
kafka-topics --create --zookeeper localhost:2181 --topic domain-data --partitions 1 --replication-factor 1
