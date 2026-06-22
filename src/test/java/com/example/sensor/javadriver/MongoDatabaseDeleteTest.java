package com.example.sensor.javadriver;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.mongodb.client.result.DeleteResult;

@SpringBootTest
@ActiveProfiles("test")
public class MongoDatabaseDeleteTest {

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
	void deleteOneTest() {
		// when
		DeleteResult result = color.deleteOne(
				Filters.eq("color", "blue")
		);
		
		// then
		assertThat(result.getDeletedCount()).isEqualTo(1);
		
		Document found = color.find(
			Filters.eq("color", "blue")
		).first();
		
		assertThat(found).isNull();
		
		assertThat(color.countDocuments()).isEqualTo(2);
	}
}
