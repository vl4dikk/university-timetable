package com.foxminded.university.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "teachers")
public class Teacher {

	@Column(name = "teacherId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int teacherId;
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;

}
