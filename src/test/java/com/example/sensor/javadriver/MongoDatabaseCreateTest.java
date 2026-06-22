package com.example.sensor.javadriver;

import static org.assertj.core.api.Assertions.assertThat;

import org.bson.Document;
import org.bson.types.ObjectId;
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
import com.mongodb.client.result.InsertOneResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class MongoDatabaseCreateTest {
	
	@Autowired
	private MongoClient mongoClient;

	@BeforeEach
	void setUp() {
		MongoDatabase db = mongoClient.getDatabase("crud_test_db");
		db.createCollection("color");
	}
	
	@AfterEach
	void tearDown() {
		MongoDatabase db = mongoClient.getDatabase("crud_test_db");
		db.drop();
	}
	
	@Test
	void insertOne() {
		// given
		MongoDatabase db = mongoClient.getDatabase("crud_test_db");
		db.createCollection("color");
		
		MongoCollection<Document> color = db.getCollection("color");
		
		Document red = new Document("color", "red")
				.append("qty", 5);
		
		InsertOneResult result = color.insertOne(red);
		
		ObjectId insertedId = result.getInsertedId().asObjectId().getValue();
		
		log.info("Inserted a document with the following id: {}", insertedId);
		
		// when
		Document found = color.find(Filters.eq("_id", insertedId)).first();
		
		// then
		assertThat(found).isNotNull();
	}
}
