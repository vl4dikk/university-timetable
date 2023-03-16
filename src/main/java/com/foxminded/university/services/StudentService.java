package com.foxminded.university.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.models.Student;

@Service
public class StudentService {

	private StudentDao dao;

	@Autowired
	public StudentService(StudentDao dao) {
		this.dao = dao;
	}

	public void insert(String firstName, String lastName) {
		Student student = new Student();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		dao.insert(student);
	}

	public void insert(HashMap<String, String> studentsNames) {
		List<Student> students = new LinkedList<>();
		for (Entry<String, String> entry : studentsNames.entrySet()) {
			String firstName = entry.getKey();
			String lastName = entry.getValue();
			Student student = new Student();
			student.setFirstName(firstName);
			student.setLastName(lastName);
			students.add(student);
		}
		dao.insert(students);
	}

	public void deleteById(int studentId) {
		dao.deleteById(studentId);
	}

	public List<Student> getAllStudents() {
		return dao.getAllStudents();
	}

	public Student getStudentById(int studentId) {
		return dao.getById(studentId);
	}

	public void assignStudentToGroup(int studentId, int groupId) {
		dao.assignStudentToGroup(studentId, groupId);
	}

}
