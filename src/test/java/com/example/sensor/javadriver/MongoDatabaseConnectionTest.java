package com.example.sensor.javadriver;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.TimeSeriesGranularity;
import com.mongodb.client.model.TimeSeriesOptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class MongoDatabaseConnectionTest {
	
	@Autowired
	private MongoClient mongoClient;
	
	@AfterEach
	void tearDown() {
		MongoDatabase db = mongoClient.getDatabase("timeseries_db");
		db.drop();
	}
	
	@Test
	void connectionTest() {
		// given
		MongoDatabase db = mongoClient.getDatabase("timeseries_db");
		
		TimeSeriesOptions timeSeriesOptions = new TimeSeriesOptions("measuredAt")
				.metaField("sensorId")
				.granularity(TimeSeriesGranularity.MINUTES);
		
		CreateCollectionOptions options = new CreateCollectionOptions()
				.timeSeriesOptions(timeSeriesOptions);
		
		db.createCollection("temperature", options);
		
		MongoCollection<Document> temperatures = db.getCollection("temperature");
		
		temperatures.insertMany(
				Arrays.asList(
				        new Document("sensorId", 1)
				            .append("measuredAt", Date.from(Instant.parse("2026-06-19T15:59:00Z")))
				            .append("temperature", 25.3)
				            .append("humidity", 42.1),

				        new Document("sensorId", 1)
				            .append("measuredAt", Date.from(Instant.parse("2026-06-19T16:00:00Z")))
				            .append("temperature", 25.4)
				            .append("humidity", 41.9),

				        new Document("sensorId", 1)
				            .append("measuredAt", Date.from(Instant.parse("2026-06-19T16:01:00Z")))
				            .append("temperature", 25.6)
				            .append("humidity", 41.7),

				        new Document("sensorId", 1)
				            .append("measuredAt", Date.from(Instant.parse("2026-06-19T16:02:00Z")))
				            .append("temperature", 25.5)
				            .append("humidity", 41.8),

				        new Document("sensorId", 2)
				            .append("measuredAt", Date.from(Instant.parse("2026-06-19T15:59:00Z")))
				            .append("temperature", 24.8)
				            .append("humidity", 45.2),

				        new Document("sensorId", 2)
				            .append("measuredAt", Date.from(Instant.parse("2026-06-19T16:00:00Z")))
				            .append("temperature", 24.9)
				            .append("humidity", 45.0),

				        new Document("sensorId", 2)
				            .append("measuredAt", Date.from(Instant.parse("2026-06-19T16:01:00Z")))
				            .append("temperature", 25.0)
				            .append("humidity", 44.8)
				)
		);
		
		// when
		Document query = new Document("sensorId", 1);
		
		List<Document> results = temperatures.find(query)
				.projection(new Document("_id", 0))
				.into(new ArrayList<>());
		
		for (Document document : results) {
			log.info("Document: {}", document.toJson());
		}
		
		// then
		assertThat(results).hasSize(4);
	}
}
