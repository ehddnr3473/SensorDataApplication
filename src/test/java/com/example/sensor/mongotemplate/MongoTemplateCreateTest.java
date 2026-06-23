package com.example.sensor.mongotemplate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.sensor.mongotemplate.entity.TestPerson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataMongoTest
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
		// given
		TestPerson person = new TestPerson("Joe", 34);
		
		// when
		mongoTemplate.insert(person, "person");
		
		TestPerson found = mongoTemplate.findById(person.getId(), TestPerson.class, "person");
		log.info("Inserted person: {}", found);
		
		// then
		assertThat(found).isNotNull();
	}
}
