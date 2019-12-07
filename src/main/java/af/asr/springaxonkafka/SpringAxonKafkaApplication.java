package af.asr.springaxonkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
@EnableMongoRepositories()
@EnableKafka
public class SpringAxonKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAxonKafkaApplication.class, args);
	}

}
