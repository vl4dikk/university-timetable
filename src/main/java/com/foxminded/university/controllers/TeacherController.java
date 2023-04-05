package com.foxminded.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.university.models.Teacher;
import com.foxminded.university.services.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

	@Autowired
	private TeacherService service;

	@GetMapping("/getAllTeachers")
	public String getAllTeachers(Model model) {
		List<Teacher> teachers = service.getAllTeachers();
		model.addAttribute("teachers", teachers);
		return "teachers/getAllTeachers";
	}

}
