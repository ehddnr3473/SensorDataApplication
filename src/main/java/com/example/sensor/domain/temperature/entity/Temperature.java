package com.example.sensor.domain.temperature.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Document(collection = "temperature")
public class Temperature {
	
    private Integer sensorId;
    
    private Double temperature;
    
    private Double humidity;
    
    private LocalDateTime measuredAt;

    public Temperature(
            Integer sensorId,
            Double temperature,
            Double humidity,
            LocalDateTime measuredAt) {

        this.sensorId = sensorId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.measuredAt = measuredAt;
    }
}
