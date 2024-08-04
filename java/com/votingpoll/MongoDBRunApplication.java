package com.votingpoll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class MongoDBRunApplication implements CommandLineRunner {

	@Autowired
	private VotingSessionRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(MongoDBRunApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		repository.save(new VotingSession(1, "n1"));
		repository.save(new VotingSession(2, "n2"));

		System.out.println(repository.findByName("n1"));
		
		System.out.println(repository.findAll());
	}

}