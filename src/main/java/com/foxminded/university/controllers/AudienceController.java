package com.foxminded.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxminded.university.models.Audience;
import com.foxminded.university.services.AudienceService;

@Controller
@RequestMapping("/audiences")
public class AudienceController {
	
	@Autowired
	private AudienceService service;

	@GetMapping("/getAllAudiences")
	public String getAllAudiences (Model model) {
		List<Audience> audiences = service.getAllAudiences();
		model.addAttribute("audiences", audiences);
		return "audiences/getAllAudiences";
	}
}
