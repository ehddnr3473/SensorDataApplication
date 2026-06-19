package com.example.sensor.domain.temperature.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.sensor.domain.temperature.entity.Temperature;

public interface TemperatureRepository extends MongoRepository<Temperature, String> {
	
	public List<Temperature> findBySensorIdAndMeasuredAtBetween(
			Integer sensorId, 
			LocalDateTime startDate, 
			LocalDateTime endDate
	);
}
