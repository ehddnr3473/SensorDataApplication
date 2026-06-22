package com.example.sensor.javadriver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class MongoDatabaseReadTest {

	@Autowired
	private MongoClient mongoClient;
	
	private MongoCollection<Document> color;
	
	@BeforeEach
	void setUp() {
		MongoDatabase db = mongoClient.getDatabase("crud_test_db");
		db.getCollection("color").drop();
		db.createCollection("color");
		
		color = db.getCollection("color");
		
		color.insertMany(List.of(
				new Document("color", "red")
				.append("qty", 5),

				new Document("color", "blue")
				.append("qty", 10),

				new Document("color", "green")
				.append("qty", 15)
		));
	}
	
	@AfterEach
	void tearDown() {
		MongoDatabase db = mongoClient.getDatabase("crud_test_db");
		db.drop();
	}
	
	@Test
	void findAllTest() {
		// when
		List<Document> result = color.find().into(new ArrayList<>());
		
		// then
		assertThat(result).hasSize(3);
	}
	
	@Test
	void findByColorTest() {
		// when
		Document result = color.find(Filters.eq("color", "red")).first();
		
		// then
		assertThat(result).isNotNull();
		assertThat(result.getInteger("qty")).isEqualTo(5);
	}
	
	@Test
	void findByGreaterThanOrEqualTest() {
		// when
		List<Document> result = color.find(Filters.gte("qty", 10)).into(new ArrayList<>());
		
		assertThat(result).hasSize(2);
	}
	
	@Test
	void findByMultipleConditionTest() {
		Document result = color.find(
				Filters.and(
						Filters.eq("color", "green"), 
						Filters.eq("qty", 15)
				)
		).first();
		
		assertThat(result).isNotNull();
	}
}
