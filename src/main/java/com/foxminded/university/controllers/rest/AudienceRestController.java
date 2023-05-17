package com.foxminded.university.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxminded.university.models.Audience;
import com.foxminded.university.services.AudienceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/audiences")
public class AudienceRestController {

	@Autowired
	private AudienceService service;

	@GetMapping
	public List<Audience> getAllAudiences() {
		return service.getAllAudiences();
	}

	@PostMapping("/add")
	public Audience addAudience(@RequestBody @Valid Audience audience, BindingResult bindingResult) {
		service.insert(audience.getAudienceNumber());
		return audience;
	}

	@GetMapping("/{id}")
	public Audience getAudienceById(@PathVariable("id") int id) {

		return service.getAudienceById(id);
	}

	@PutMapping("/{id}")
	public Audience updateAudience(@PathVariable("id") int id, @RequestBody @Valid Audience audience,
			BindingResult bindingResult) {
		service.update(audience);
		return audience;
	}

	@DeleteMapping("/{id}")
	public void deleteAudience(@PathVariable("id") int id) {
		service.deleteById(id);
	}
}
