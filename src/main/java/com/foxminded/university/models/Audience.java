package com.foxminded.university.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@Table(name = "audiences")
public class Audience {

	@Column(name = "audienceid")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int audienceId;
	@Column(name = "audiencenumber", nullable = true, unique = true)
	@Min(value = 1)
	private int audienceNumber;

}
