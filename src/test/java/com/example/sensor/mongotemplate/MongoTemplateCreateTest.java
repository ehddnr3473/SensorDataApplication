package com.example.sensor.mongotemplate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.sensor.mongotemplate.entity.TestPerson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class MongoTemplateCreateTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@AfterEach
	void tearDown() {
		mongoTemplate.dropCollection("person");
	}
	
	@Test
	void insertOneTest() {
		TestPerson person = new TestPerson("Joe", 34);
		
		mongoTemplate.insert(person, "person");
		
		TestPerson found = mongoTemplate.findById(person.getId(), TestPerson.class);
		log.info("Inserted person: {}", found);
		
		assertThat(found).isNotNull();
	}
}
