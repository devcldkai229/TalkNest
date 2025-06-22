package com.backend.TalkNestResourceServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class TalkNestResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkNestResourceServerApplication.class, args);
	}
}
