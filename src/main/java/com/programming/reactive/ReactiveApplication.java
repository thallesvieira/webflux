package com.programming.reactive;

import com.programming.reactive.domain.model.PubSubMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Sinks;

@SpringBootApplication
public class ReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveApplication.class, args);
	}

	@Bean
	public Sinks.Many<PubSubMessage> sink() {
		return Sinks.many().multicast()
				.onBackpressureBuffer(100);
	}
}
