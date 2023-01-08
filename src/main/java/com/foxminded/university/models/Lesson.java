package com.foxminded.university.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Lesson {

	private String name;
	private Teacher teacher;
	private Group group;
	private Audience audience;
	private LocalDateTime time;

	public Lesson(String name, Teacher teacher, Group group, Audience audience, LocalDateTime time) {
		super();
		this.name = name;
		this.teacher = teacher;
		this.group = group;
		this.audience = audience;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Audience getAudience() {
		return audience;
	}

	public void setAudience(Audience audience) {
		this.audience = audience;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	@Override
	public int hashCode() {
		return Objects.hash(audience, group, name, teacher, time);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lesson other = (Lesson) obj;
		return Objects.equals(audience, other.audience) && Objects.equals(group, other.group)
				&& Objects.equals(name, other.name) && Objects.equals(teacher, other.teacher)
				&& Objects.equals(time, other.time);
	}

	@Override
	public String toString() {
		return "Lesson [name=" + name + ", teacher=" + teacher + ", group=" + group + ", audience=" + audience
				+ ", time=" + time + "]";
	}

}
