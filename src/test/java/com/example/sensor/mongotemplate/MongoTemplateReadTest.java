package com.example.sensor.mongotemplate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

import com.example.sensor.mongotemplate.entity.TestPerson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class MongoTemplateReadTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@BeforeEach
	void setUp() {
        mongoTemplate.insert(
        		List.of(
        				new TestPerson("Joe", 34), 
        				new TestPerson("Kim", 31), 
        				new TestPerson("Lee", 32)
        		), 
        		"person"
        );
	}
	
	@AfterEach
	void tearDown() {
		mongoTemplate.dropCollection("person");
	}
	
	@Test
	void findAllTest() {
		// when
		List<TestPerson> persons = mongoTemplate.findAll(TestPerson.class, "person");
		
		// then
		assertThat(persons).hasSize(3);
		
		assertThat(persons)
			.extracting(TestPerson::getName)
			.containsExactlyInAnyOrder("Joe", "Kim", "Lee");
	}
	
	@Test
	void findByAgeGreaterThanTest() {
		// when
		List<TestPerson> persons = mongoTemplate.find(
				Query.query(
						Criteria
							.where("age")
							.gt(31)
				).with(
						Sort.by(
								Sort.Direction.ASC, 
								"name"
						)
				), 
				TestPerson.class, 
				"person"
		);
		
		assertThat(persons).hasSize(2);
		assertThat(persons)
			.extracting(TestPerson::getName)
			.containsExactly("Joe", "Lee");
	}
}
