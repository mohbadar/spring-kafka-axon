package af.asr.springaxonkafka.config.kafka;

import af.asr.springaxonkafka.config.kafka.message.KafkaMessage;

public interface Sender {

    void send(final KafkaMessage message);
}