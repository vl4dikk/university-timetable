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

import com.foxminded.university.models.Group;
import com.foxminded.university.services.GroupService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/groups")
public class GroupRestController {

	@Autowired
	private GroupService service;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Group>>> getAllGroups() {
		List<Group> groups = service.getAllGroups();
		List<EntityModel<Group>> groupModels = new ArrayList<EntityModel<Group>>();
		for (Group group : groups) {
			EntityModel<Group> groupModel = EntityModel.of(group);
			Link selfLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(GroupRestController.class).getGroupById(group.getId()))
					.withSelfRel();
			groupModel.add(selfLink);
			groupModels.add(groupModel);
		}
		CollectionModel<EntityModel<Group>> collectionModel = CollectionModel.of(groupModels);
		return ResponseEntity.ok(collectionModel);
	}

	@PostMapping
	public ResponseEntity<Void> addGroup(@RequestBody @Valid Group group) {
		service.insert(group.getName());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Group>> getGroupById(@PathVariable("id") int id) {
		Group group = service.getGroupById(id);
		if (group == null) {
			return ResponseEntity.notFound().build();
		}
		EntityModel<Group> groupModel = EntityModel.of(group);
		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupRestController.class).getGroupById(id))
				.withSelfRel();
		groupModel.add(selfLink);
		return ResponseEntity.ok(groupModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Group>> updateGroup(@PathVariable("id") int id, @RequestBody @Valid Group group) {
		service.update(group);
		EntityModel<Group> groupModel = EntityModel.of(group);
		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupRestController.class).getGroupById(id))
				.withSelfRel();
		groupModel.add(selfLink);
		return ResponseEntity.ok(groupModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGroup(@PathVariable("id") int id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
