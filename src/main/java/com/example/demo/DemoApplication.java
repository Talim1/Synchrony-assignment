package com.example.demo;

import com.example.demo.config.KafkaConsumerConfig;
import com.example.demo.config.KafkaProducerConfig;
import com.example.demo.config.KafkaTopicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Import(value = { KafkaTopicConfig.class, KafkaConsumerConfig.class, KafkaProducerConfig.class })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
