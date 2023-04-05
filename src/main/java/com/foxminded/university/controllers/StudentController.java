package com.foxminded.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.university.models.Student;
import com.foxminded.university.services.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService service;

	@GetMapping("/getAllStudents")
	public String getAllStudents(Model model) {
		List<Student> students = service.getAllStudents();
		model.addAttribute("students", students);
		return "students/getAllStudents";
	}

}
