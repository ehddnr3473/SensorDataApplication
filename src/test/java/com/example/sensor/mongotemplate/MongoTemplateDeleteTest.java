package com.example.sensor.mongotemplate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

import com.example.sensor.mongotemplate.entity.TestPerson;
import com.mongodb.client.result.DeleteResult;

@SpringBootTest
@ActiveProfiles("test")
public class MongoTemplateDeleteTest {

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
	void deleteOneTest() {
		// when
		DeleteResult result = mongoTemplate.remove(
				Query.query(Criteria.where("name").is("Kim")), 
				"person"
		);
		
		// then
		assertThat(result.getDeletedCount()).isEqualTo(1);
		
		TestPerson found = mongoTemplate.findOne(
				Query.query(Criteria.where("name").is("Kim")), 
				TestPerson.class, 
				"person"
		);
		
		assertThat(found).isNull();
	}
}
