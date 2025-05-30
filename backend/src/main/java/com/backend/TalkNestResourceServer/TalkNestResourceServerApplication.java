package com.backend.TalkNestResourceServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class TalkNestResourceServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TalkNestResourceServerApplication.class, args);
	}
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) {
		try {
			String result = jdbcTemplate.queryForObject("SELECT version()", String.class);
			System.out.println(" PostgreSQL connected successfully: " + result);
		} catch (Exception e) {
			System.err.println(" Cannot connect to PostgreSQL: " + e.getMessage());
		}
	}
}
