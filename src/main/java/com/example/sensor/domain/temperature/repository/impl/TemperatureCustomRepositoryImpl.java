package com.example.sensor.domain.temperature.repository.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.example.sensor.domain.temperature.repository.TemperatureCustomRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TemperatureCustomRepositoryImpl implements TemperatureCustomRepository {

	private final MongoTemplate mongoTemplate;
	
	
}
