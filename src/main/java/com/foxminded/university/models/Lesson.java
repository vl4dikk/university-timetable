package com.foxminded.university.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "lessons")
public class Lesson {

	@Column(name = "lessonId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lessonId;
	@Column(name = "name")
	private String name;
	@OneToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;
	@OneToOne
	@JoinColumn(name = "group_id")
	private Group group;
	@OneToOne
	@JoinColumn(name = "audience_id")
	private Audience audience;
	@Column(name = "ltime")
	private LocalDateTime time;

}
