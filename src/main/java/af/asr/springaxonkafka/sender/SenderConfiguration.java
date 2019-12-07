package af.asr.springaxonkafka.sender;


//import af.asr.springaxonkafka.config.KafkaConfigBuilder;
//import lombok.ToString;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.common.serialization.ByteArrayDeserializer;
//import org.apache.kafka.common.serialization.ByteArraySerializer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import javax.annotation.PostConstruct;
//import java.util.Properties;
//import java.util.UUID;
//
//@Configuration
//@EnableSwagger2
//@Slf4j
//@ToString
//public class SenderConfiguration {
//
//    @Value("${kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    @Value("${kafka.event-messaging}")
//    private String eventMessagingTopic;
//
//    @Value("${kafka.event-storage}")
//    private String eventStorageTopic;
//
//    @Value("${kafka.timeout:1000}")
//    private Long timeout;
//
//    @PostConstruct
//    void log() {
//        log.info(this.toString());
//    }
//
//    @Bean
//    public Properties producerConfigs() {
//        return KafkaConfigBuilder.defaultProducer().bootstrapServers(bootstrapServers).withKeySerializer(StringSerializer.class)
//                .withValueSerializer(ByteArraySerializer.class).build();
//    }
//
//    @Bean
//    public Properties consumerConfigs() {
//        return KafkaConfigBuilder.defaultConsumer().bootstrapServers(bootstrapServers).withKeyDeserializer(StringDeserializer.class)
//                .withValueDeserializer(ByteArrayDeserializer.class).group(UUID.randomUUID().toString()).build();
//    }
//}
