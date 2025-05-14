package com.example.progressBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProgressBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgressBoardApplication.class, args);
	}
}
