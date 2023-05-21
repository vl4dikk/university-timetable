package com.foxminded.university.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "teachers")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Teacher {

	@Column(name = "teacherId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int teacherId;
	@Column(name = "firstName")
	@NotBlank
	private String firstName;
	@Column(name = "lastName")
	@NotBlank
	private String lastName;
	@Column(name = "email")
	@Email(regexp = "|^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	private String email;
}
