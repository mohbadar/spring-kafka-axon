package af.asr.springaxonkafka.config.kafka;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "kafka")
@Slf4j
public class TopicsConfiguration {

    @Autowired
    private GenericWebApplicationContext context;

    private List<TopicConfiguration> topics;

    public Optional<List<TopicConfiguration>> getTopics() {
        return Optional.ofNullable(topics);
    }

    @Data
    static class TopicConfiguration {
        @NotNull(message = "Topic name is required.")
        private String name;
        private Integer numPartitions = 3;
        private Short replicationFactor = 1;

        NewTopic toNewTopic() {
            return new NewTopic(this.name, this.numPartitions, this.replicationFactor);
        }
    }


    public void initializeBeans(List<TopicConfiguration> topics) {
        log.info("Configuring {} topics", topics.size());
        topics.forEach(t -> {
            log.info(
                    "topic={},numPartitions={},replicationFactor={}",
                    t.getName(),
                    t.getNumPartitions(),
                    t.getReplicationFactor()
            );
            context.registerBean(t.getName(), NewTopic.class, t::toNewTopic);
        });
    }


}