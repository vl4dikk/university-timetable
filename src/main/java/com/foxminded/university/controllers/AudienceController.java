package com.foxminded.university.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.foxminded.university.models.Audience;
import com.foxminded.university.services.AudienceService;

import jakarta.validation.Valid;

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
	public String deleteAudience(@PathVariable(value = "id") int id) {
		service.deleteById(id);
		return "redirect:/audiences/getAllAudiences";
	}

	@GetMapping("/addNewAudienceForm")
	public String addNewAudienceForm(Model model) {
		List<Audience> audiences = service.getAllAudiences();
		List<Integer> existingNumbers = new LinkedList<>();
		for (Audience audience : audiences) {
			existingNumbers.add(audience.getAudienceNumber());
		}
		Audience audience = new Audience();
		model.addAttribute("existingNumbers", existingNumbers);
		model.addAttribute("audience", audience);
		return "audiences/addNewAudienceForm";
	}

	@PostMapping("/insertAudience")
	public String insertAudience(@ModelAttribute("audience") @Valid Audience audience, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation exception");
		}
		service.insert(audience.getAudienceNumber());
		return "redirect:/audiences/getAllAudiences";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
		Audience audience = service.getAudienceById(id);
		List<Audience> audiences = service.getAllAudiences();
		List<Integer> existingNumbers = new LinkedList<>();
		for (Audience audienceFromList : audiences) {
			existingNumbers.add(audienceFromList.getAudienceNumber());
		}
		model.addAttribute("audience", audience);
		model.addAttribute("existingNumbers", existingNumbers);
		return "audiences/updateAudienceForm";
	}

	@PostMapping("/updateAudience")
	public String updateAudience(@ModelAttribute("audience") @Valid Audience audience) {
		service.update(audience);
		return "redirect:/audiences/getAllAudiences";
	}
}
