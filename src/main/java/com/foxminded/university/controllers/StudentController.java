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

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService service;
	@Autowired
	private GroupService groupService;

	@GetMapping("/getAllStudents")
	public String getAllStudents(Model model) {
		List<Student> students = service.getAllStudents();
		model.addAttribute("students", students);
		return "students/getAllStudents";
	}
	
	@GetMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable (value = "id") int id) {
		service.deleteById(id);
		return "redirect:/students/getAllStudents";
	}
	
	@GetMapping("/addNewStudentForm")
	public String addNewStudentForm(Model model) {
		List<Group> groups = groupService.getAllGroups();
		Student student = new Student();
		model.addAttribute("groups", groups);
		model.addAttribute("student", student);
		return "students/addNewStudentForm";
	}
	
	@PostMapping("/insertStudent")
	public String insertStudent(@ModelAttribute("student") Student student) {
			service.insert(student);
		return "redirect:/students/getAllStudents";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") int id, Model model) {	
		Student student = service.getStudentById(id);
		List<Group> groups = groupService.getAllGroups();
		model.addAttribute("groups", groups);
		model.addAttribute("student", student);
		return "students/updateStudentForm";
	}
	
	@PostMapping("/updateStudent")
	public String updateStudent(@ModelAttribute("student") Student student) {
			service.update(student);
		return "redirect:/students/getAllStudents";
	}

}
