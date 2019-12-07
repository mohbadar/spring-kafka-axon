package af.asr.springaxonkafka.config.eventsourcing;

import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.AbstractDomainEventEntry;
import org.axonframework.serialization.Serializer;

public class KafkaDomainEventEntry extends AbstractDomainEventEntry<byte[]> {

    public KafkaDomainEventEntry(final DomainEventMessage<?> eventMessage, Serializer serializer) {
        super(eventMessage, serializer, byte[].class);
    }
}