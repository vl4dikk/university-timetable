package com.foxminded.university.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.models.Student;

@Service
public class StudentService {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

	private StudentDao dao;

	@Autowired
	public StudentService(StudentDao dao) {
		this.dao = dao;
	}

	public void insert(String firstName, String lastName) {
		logger.info("Started StudentService to insert student");
		Student student = new Student();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		dao.insert(student);
		logger.info("Student inserted");
	}

	public void insert(HashMap<String, String> studentsNames) {
		logger.info("Started StudentService to insert list of students");
		for (Entry<String, String> entry : studentsNames.entrySet()) {
			String firstName = entry.getKey();
			String lastName = entry.getValue();
			Student student = new Student();
			student.setFirstName(firstName);
			student.setLastName(lastName);
			dao.insert(student);
			logger.info("Student inserted");
		}
	}

	public void deleteById(int studentId) {
		logger.info("Started StudentService to delete student by id");
		dao.deleteById(studentId);
	}

	public List<Student> getAllStudents() {
		logger.info("Started StudentService to get all students");
		return dao.getAllStudents();
	}

	public Student getStudentById(int studentId) {
		logger.info("Started StudentService to get student by id");
		return dao.getById(studentId);
	}

	public void assignStudentToGroup(int studentId, int groupId) {
		logger.info("Started StudentService to assign student to group");
		dao.assignStudentToGroup(studentId, groupId);
	}

}
