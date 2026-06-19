package com.example.sensor.domain.temperature.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sensor.domain.temperature.dto.request.SelectTemperaturesRequestDTO;
import com.example.sensor.domain.temperature.dto.response.SelectTemperaturesResponseDTO;
import com.example.sensor.domain.temperature.repository.TemperatureCustomRepository;
import com.example.sensor.domain.temperature.repository.TemperatureRepository;
import com.example.sensor.domain.temperature.service.TemperatureService;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.TimeSeriesGranularity;
import com.mongodb.client.model.TimeSeriesOptions;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemperatureServiceImpl implements TemperatureService {

	private final TemperatureRepository temperatureRepository;
	private final TemperatureCustomRepository temperatureCustomRepository;
	
	public List<SelectTemperaturesResponseDTO> findTemperatures(SelectTemperaturesRequestDTO requestDTO) {
		return temperatureRepository
				.findBySensorIdAndMeasuredAtBetween(
						requestDTO.getSensorId(), 
						LocalDate.parse(requestDTO.getStartDate()).atStartOfDay(), 
						LocalDate.parse(requestDTO.getEndDate()).atTime(LocalTime.MAX))
				.stream()
				.map(SelectTemperaturesResponseDTO::from)
				.toList();
	}
}
