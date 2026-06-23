package com.example.sensor.javadriver;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataMongoTest
@ActiveProfiles("test")
public class MongoDatabaseUpdateTest {

	@Autowired
	private MongoClient mongoClient;
	
	private MongoCollection<Document> color;
	
	@BeforeEach
	void setUp() {
		MongoDatabase db = mongoClient.getDatabase("crud_test_db");
		db.getCollection("color").drop();
		db.createCollection("color");
		
		color = db.getCollection("color");
		
		color.insertOne(new Document("color", "red").append("qty", 5));
	}
	
	@AfterEach
	void tearDown() {
		MongoDatabase db = mongoClient.getDatabase("crud_test_db");
		db.drop();
	}
	
	@Test
	void updateOneTest() {
		// when
		UpdateResult result = color.updateOne(
				Filters.eq("color", "red"), 
				Updates.set("qty", 10)
		);
		
		// then
		assertThat(result.getMatchedCount()).isEqualTo(1);
		assertThat(result.getModifiedCount()).isEqualTo(1);
		
		Document found = color.find(Filters.eq("color", "red")).first();
		
		log.info("Document: {}", found.toJson());
		
		assertThat(found).isNotNull();
		assertThat(found.getInteger("qty")).isEqualTo(10);
	}
	
	@Test
	void updateOneMultipleConditionTest() {
		// when
		UpdateResult result = color.updateOne(
				Filters.eq("color", "red"), 
				Updates.combine(
						Updates.set("qty", 10), 
						Updates.set("color", "blue")
				)
		);
		
		log.info("result: {}", result);
		
		Document found = color.find(Filters.eq("color", "blue")).first();
		
		// then
		assertThat(found.getInteger("qty")).isEqualTo(10);
	}
}
