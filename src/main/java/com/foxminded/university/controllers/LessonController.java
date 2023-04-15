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

import com.foxminded.university.models.Audience;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lesson;
import com.foxminded.university.models.Teacher;
import com.foxminded.university.services.AudienceService;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.LessonService;
import com.foxminded.university.services.TeacherService;

@Controller
@RequestMapping("/lessons")
public class LessonController {

	@Autowired
	private LessonService service;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private AudienceService audienceService;

	@GetMapping("/getAllLessons")
	public String getAllLessons(Model model) {
		List<Lesson> lessons = service.getAllLessons();
		model.addAttribute("lessons", lessons);
		return "lessons/getAllLessons";
	}

	@GetMapping("/deleteLesson/{id}")
	public String deleteLesson(@PathVariable(value = "id") int id) {
		service.deleteById(id);
		return "redirect:/lessons/getAllLessons";
	}

	@GetMapping("/addNewLessonForm")
	public String addNewLessonForm(Model model) {
		Lesson lesson = new Lesson();
		List<Teacher> teachers = teacherService.getAllTeachers();
		List<Group> groups = groupService.getAllGroups();
		List<Audience> audiences = audienceService.getAllAudiences();
		model.addAttribute("lesson", lesson);
		model.addAttribute("teachers", teachers);
		model.addAttribute("groups", groups);
		model.addAttribute("audiences", audiences);
		return "lessons/addNewLessonForm";
	}

	@PostMapping("/insertLesson")
	public String insertLesson(@ModelAttribute("lesson") Lesson lesson) {
		service.insert(lesson.getName(), lesson.getTeacher().getTeacherId(), lesson.getGroup().getId(),
				lesson.getAudience().getAudienceId(), lesson.getTime());
		return "redirect:/lessons/getAllLessons";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
		Lesson lesson = service.getById(id);
		List<Teacher> teachers = teacherService.getAllTeachers();
		List<Group> groups = groupService.getAllGroups();
		List<Audience> audiences = audienceService.getAllAudiences();
		model.addAttribute("lesson", lesson);
		model.addAttribute("teachers", teachers);
		model.addAttribute("groups", groups);
		model.addAttribute("audiences", audiences);
		return "lessons/updateLessonForm";
	}

	@PostMapping("/updateLesson")
	public String updateLesson(@ModelAttribute("lesson") Lesson lesson) {
		service.update(lesson);
		return "redirect:/lessons/getAllLessons";
	}

}
