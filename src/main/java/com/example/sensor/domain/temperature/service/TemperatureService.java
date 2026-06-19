package com.example.sensor.domain.temperature.service;

import java.util.List;

import com.example.sensor.domain.temperature.dto.request.SelectTemperaturesRequestDTO;
import com.example.sensor.domain.temperature.dto.response.SelectTemperaturesResponseDTO;

public interface TemperatureService {

	public List<SelectTemperaturesResponseDTO> findTemperatures(SelectTemperaturesRequestDTO selectTemperaturesRequestDTO);
}
