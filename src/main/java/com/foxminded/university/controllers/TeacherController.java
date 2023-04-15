package com.foxminded.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/deleteTeacher/{id}")
	public String deleteTeacher(@PathVariable(value = "id") int id) {
		service.deleteById(id);
		return "redirect:/teachers/getAllTeachers";
	}

	@GetMapping("/addNewTeacherForm")
	public String addNewTeacherForm(Model model) {
		Teacher teacher = new Teacher();
		model.addAttribute("teacher", teacher);
		return "teachers/addNewTeacherForm";
	}

	@PostMapping("/insertTeacher")
	public String insertStudent(@ModelAttribute("teacher") Teacher teacher) {
		service.insert(teacher.getFirstName(), teacher.getLastName());
		return "redirect:/teachers/getAllTeachers";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
		Teacher teacher = service.getTeacherById(id);
		model.addAttribute("teacher", teacher);
		return "teachers/updateTeacherForm";
	}

	@PostMapping("/updateTeacher")
	public String updateStudent(@ModelAttribute("teacher") Teacher teacher) {
		service.update(teacher);
		return "redirect:/teachers/getAllTeachers";
	}

}
