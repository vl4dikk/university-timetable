package com.foxminded.university.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "students")
public class Student {

	@Column(name = "studentid")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;
	@Column(name = "firstname")
	@NotBlank
	private String firstName;
	@Column(name = "lastname")
	@NotBlank
	private String lastName;
	@OneToOne
	@JoinColumn(name = "group_id")
	private Group group;

}
