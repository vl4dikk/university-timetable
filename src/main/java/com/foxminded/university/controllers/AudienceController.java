package com.foxminded.university.controllers;

import java.util.LinkedList;
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
import com.foxminded.university.services.AudienceService;

@Controller
@RequestMapping("/audiences")
public class AudienceController {

	@Autowired
	private AudienceService service;

	@GetMapping("/getAllAudiences")
	public String getAllAudiences(Model model) {
		List<Audience> audiences = service.getAllAudiences();
		model.addAttribute("audiences", audiences);
		return "audiences/getAllAudiences";
	}
	
	@GetMapping("/deleteAudience/{id}")
	public String deleteGroup(@PathVariable (value = "id") int id) {
		service.deleteById(id);
		return "redirect:/audiences/getAllAudiences";
	}
	
	@GetMapping("/addNewAudienceForm")
	public String addNewAudienceForm(Model model) {
		List<Audience> audiences = service.getAllAudiences();
		List<Integer> existingNumbers = new LinkedList<>();
		for(Audience audience : audiences) {
			existingNumbers.add(audience.getAudienceId());
		}
		Audience audience = new Audience();
		model.addAttribute("existingNumbers", existingNumbers);
		model.addAttribute("audience", audience);
		return "audiences/addNewAudienceForm";
	}
	
	@PostMapping("/insertAudience")
	public String insertGroup(@ModelAttribute("audience") Audience audience) {
		service.insert(audience.getAudienceNumber());
		return "redirect:/audiences/getAllAudiences";
	}
}
