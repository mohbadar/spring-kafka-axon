package af.asr.springaxonkafka.config.kafka;

import java.util.Collections;
import java.util.Optional;

import af.asr.springaxonkafka.config.kafka.message.KafkaMessageConverter;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class KafkaMessageSource {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessageSource.class);

    private KafkaMessageConverter converter;
    private DefaultSubscribableEventSource source;

    public KafkaMessageSource(Serializer serializer, DefaultSubscribableEventSource source) {
        this.source = source;
        this.converter = new KafkaMessageConverter(serializer);
    }

    public void receive(byte[] value) {
        if (!source.getEventProcessors().isEmpty()) {
            final Optional<EventMessage<?>> createAxonEventMessage = converter.createAxonEventMessage(value);
            if (!createAxonEventMessage.isPresent()) {
                log.warn("Unable to read message. Ignoring it.");
            } else {
                log.info("Distributing the message to {} eventProcessors", source.getEventProcessors().size());
                source.getEventProcessors().forEach(ep -> ep.accept(Collections.singletonList(createAxonEventMessage.get())));
            }
        } else {
            log.warn("No event processors found.");
        }
    }
}