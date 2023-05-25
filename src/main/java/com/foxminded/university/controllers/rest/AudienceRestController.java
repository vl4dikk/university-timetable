package com.foxminded.university.controllers.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/audiences")
@Tag(description = "Audiences controller", name = "Audiences")
public class AudienceRestController {

	@Autowired
	private AudienceService service;

	@GetMapping
	@Operation(summary ="Get all audiences", description = "")
	public ResponseEntity<CollectionModel<EntityModel<Audience>>> getAllAudiences() {
		List<Audience> audiences = service.getAllAudiences();
		List<EntityModel<Audience>> audienceModels = new ArrayList<EntityModel<Audience>>();
		for (Audience audience : audiences) {
			EntityModel<Audience> audienceModel = EntityModel.of(audience);
			Link selfLink = WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(AudienceRestController.class).getAudienceById(audience.getAudienceId()))
					.withSelfRel();
			audienceModel.add(selfLink);
			audienceModels.add(audienceModel);
		}
		CollectionModel<EntityModel<Audience>> collectionModel = CollectionModel.of(audienceModels);
		return ResponseEntity.ok(collectionModel);
	}

	@PostMapping
	@Operation(summary = "Add new audience")
	public ResponseEntity<Void> addAudience(@RequestBody @Valid Audience audience) {
		service.insert(audience.getAudienceNumber());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	 @Operation(description = "Get an audience by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Audience not found")
    })
	public ResponseEntity<EntityModel<Audience>> getAudienceById(@PathVariable("id") int id) {
		Audience audience = service.getAudienceById(id);
		if (audience == null) {
			return ResponseEntity.notFound().build();
		}
		EntityModel<Audience> audienceModel = EntityModel.of(audience);
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AudienceRestController.class).getAudienceById(id)).withSelfRel();
		audienceModel.add(selfLink);
		return ResponseEntity.ok(audienceModel);
	}

	@PutMapping("/{id}")
    @Operation(description = "Update an existing audience")
	public ResponseEntity<EntityModel<Audience>> updateAudience(@PathVariable("id") int id,
			@RequestBody @Valid Audience audience) {
		service.update(audience);
		EntityModel<Audience> audienceModel = EntityModel.of(audience);
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AudienceRestController.class).getAudienceById(id)).withSelfRel();
		audienceModel.add(selfLink);
		return ResponseEntity.ok(audienceModel);
	}

	@DeleteMapping("/{id}")
	@Operation(description = "Delete an audience")
	public ResponseEntity<Void> deleteAudience(@PathVariable("id") int id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
