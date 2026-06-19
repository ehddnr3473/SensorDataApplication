package com.example.sensor.domain.temperature.dto.response;

import java.time.LocalDateTime;

import com.example.sensor.domain.temperature.entity.Temperature;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SelectTemperaturesResponseDTO {

	@Schema(description = "센서 번호")
    private Integer sensorId;

    @Schema(description = "온도")
    private Double temperature;

    @Schema(description = "습도")
    private Double humidity;

    @Schema(description = "측정일시")
    private LocalDateTime measuredAt;
    
    public static SelectTemperaturesResponseDTO from(Temperature temperature) {
    	return SelectTemperaturesResponseDTO.builder()
    			.sensorId(temperature.getSensorId())
    			.temperature(temperature.getTemperature())
    			.humidity(temperature.getHumidity())
    			.measuredAt(temperature.getMeasuredAt())
    			.build();
    }
}
