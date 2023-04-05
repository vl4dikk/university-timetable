package com.foxminded.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.university.models.Lesson;
import com.foxminded.university.services.LessonService;

@Controller
@RequestMapping("/lessons")
public class LessonController {

	@Autowired
	private LessonService service;

	@GetMapping("/getAllLessons")
	public String getAllLessons(Model model) {
		List<Lesson> lessons = service.getAllLessons();
		model.addAttribute("lessons", lessons);
		return "lessons/getAllLessons";
	}

}
