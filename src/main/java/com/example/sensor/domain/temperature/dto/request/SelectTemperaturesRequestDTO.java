package com.example.sensor.domain.temperature.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectTemperaturesRequestDTO {

	@Schema(description = "센서 번호", example = "1")
    private Integer sensorId;

    @Schema(description = "조회 시작일시", example = "2026-06-18")
    private String startDate;

    @Schema(description = "조회 종료일시", example = "2026-06-18")
    private String endDate;
}
