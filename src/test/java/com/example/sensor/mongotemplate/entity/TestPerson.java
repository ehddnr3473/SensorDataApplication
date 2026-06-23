package com.example.sensor.mongotemplate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestPerson {
	
	private String id;
	private String name;
	private Integer age;

	public TestPerson(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
}
