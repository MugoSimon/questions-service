package com.mugosimon.question_service;

import com.mugosimon.question_service.utils.PortListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class QuestionServiceApplication implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private PortListener portListener;

	public static void main(String[] args) {
		SpringApplication.run(QuestionServiceApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		int port = portListener.getPort();
		System.out.println("***   ***   ***   ***   ***   ***   ***   ***   ***");
		System.out.println("Question Service Started\nrunning on port: " + port);
		System.out.println("***   ***   ***   ***   ***   ***   ***   ***   ***");
	}
}

