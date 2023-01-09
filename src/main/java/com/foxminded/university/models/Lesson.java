package com.foxminded.university.models;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Lesson {

	private String name;
	private Teacher teacher;
	private Group group;
	private Audience audience;
	private LocalDateTime time;

}
