package com.foxminded.university.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Teacher;

@Service
public class TeacherService {

	private TeacherDao dao;

	@Autowired
	public TeacherService(TeacherDao dao) {
		this.dao = dao;
	}

	public void insert(String firstName, String lastName) {
		Teacher teacher = new Teacher();
		teacher.setFirstName(firstName);
		teacher.setLastName(lastName);
		dao.insert(teacher);
	}

	public void insert(HashMap<String, String> teachersNames) {
		for (Entry<String, String> entry : teachersNames.entrySet()) {
			String firstName = entry.getKey();
			String lastName = entry.getValue();
			Teacher teacher = new Teacher();
			teacher.setFirstName(firstName);
			teacher.setLastName(lastName);
			dao.insert(teacher);
		}
	}

	public void deleteById(int teacherId) {
		dao.deleteById(teacherId);
	}

	public List<Teacher> getAllTeachers() {
		return dao.getAllTeachers();
	}

	public Teacher getTeacherById(int teacherId) {
		return dao.getTeacherById(teacherId);
	}
}
