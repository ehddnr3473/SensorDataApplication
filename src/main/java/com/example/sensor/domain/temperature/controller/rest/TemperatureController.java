package com.example.sensor.domain.temperature.controller.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sensor.domain.temperature.dto.request.SelectTemperaturesRequestDTO;
import com.example.sensor.domain.temperature.dto.response.SelectTemperaturesResponseDTO;
import com.example.sensor.domain.temperature.service.TemperatureService;
import com.example.sensor.global.result.ApiResponse;
import com.example.sensor.global.result.ResultCode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "기온")
@RestController
@RequestMapping("/temperature")
@RequiredArgsConstructor
public class TemperatureController {
	
	private final TemperatureService temperatureService;

	@Operation(summary = "기온 조회")
	@PostMapping("/ajax/selectTemperatures.json")
	public ResponseEntity<ApiResponse<List<SelectTemperaturesResponseDTO>>> selectTemperatures(
			@RequestBody SelectTemperaturesRequestDTO selectTemperaturesRequestDTO
	) {
		return ResponseEntity
				.ok(ApiResponse.success(ResultCode.SUCCESS, temperatureService.findTemperatures(selectTemperaturesRequestDTO)));
	}
}
