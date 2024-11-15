package br.com.microservices.orchestrated.orchestratorservice.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;

	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	private Map<String, Object> consumerConfigs() {
		return Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
				ConsumerConfig.GROUP_ID_CONFIG, groupId,
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset
		);
	}

	@Bean
	public ProducerFactory<String, String> producerFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	private Map<String, Object> producerConfigs() {
		return Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
		);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
}
