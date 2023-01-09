package com.foxminded.university.models;

import lombok.Data;

@Data
public class Student {

	private int studentId;
	private String firstName;
	private String lastName;
	private Group group;

}
