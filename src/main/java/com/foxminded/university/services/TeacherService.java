package com.foxminded.university.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Teacher;

@Service
public class TeacherService {
	
	private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

	private TeacherDao dao;

	@Autowired
	public TeacherService(TeacherDao dao) {
		this.dao = dao;
	}

	public void insert(String firstName, String lastName) {
		logger.info("Started TeacherService to insert teacher");
		Teacher teacher = new Teacher();
		teacher.setFirstName(firstName);
		teacher.setLastName(lastName);
		dao.insert(teacher);
		logger.info("Teacher inserted");
	}

	public void insert(HashMap<String, String> teachersNames) {
		logger.info("Started TeacherService to insert list of teachers");
		for (Entry<String, String> entry : teachersNames.entrySet()) {
			String firstName = entry.getKey();
			String lastName = entry.getValue();
			Teacher teacher = new Teacher();
			teacher.setFirstName(firstName);
			teacher.setLastName(lastName);
			dao.insert(teacher);
			logger.info("Teacher inserted");
		}
	}

	public void deleteById(int teacherId) {
		logger.info("Started TeacherService to delete teacher by id");
		dao.deleteById(teacherId);
	}

	public List<Teacher> getAllTeachers() {
		logger.info("Started TeacherService to get all teachers");
		return dao.getAllTeachers();
	}

	public Teacher getTeacherById(int teacherId) {
		logger.info("Started TeacherService to get teacher by id");
		return dao.getTeacherById(teacherId);
	}
}
