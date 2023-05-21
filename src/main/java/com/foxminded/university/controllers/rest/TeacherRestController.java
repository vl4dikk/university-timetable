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

import com.foxminded.university.models.Teacher;
import com.foxminded.university.services.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teachers")
public class TeacherRestController {

	@Autowired
	private TeacherService service;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Teacher>>> getAllTeachers() {
		List<Teacher> teachers = service.getAllTeachers();
		List<EntityModel<Teacher>> teacherModels = new ArrayList<EntityModel<Teacher>>();
		for (Teacher teacher : teachers) {
			EntityModel<Teacher> teacherModel = EntityModel.of(teacher);
			Link selfLink = WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(TeacherRestController.class).getTeacherById(teacher.getTeacherId()))
					.withSelfRel();
			teacherModel.add(selfLink);
			teacherModels.add(teacherModel);
		}
		CollectionModel<EntityModel<Teacher>> collectionModel = CollectionModel.of(teacherModels);
		return ResponseEntity.ok(collectionModel);
	}

	@PostMapping
	public ResponseEntity<Void> addTeacher(@RequestBody @Valid Teacher teacher) {
		service.insert(teacher.getFirstName(), teacher.getLastName());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Teacher>> getTeacherById(@PathVariable("id") int id) {
		Teacher teacher = service.getTeacherById(id);
		if (teacher == null) {
			return ResponseEntity.notFound().build();
		}
		EntityModel<Teacher> teacherModel = EntityModel.of(teacher);
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(TeacherRestController.class).getTeacherById(id)).withSelfRel();
		teacherModel.add(selfLink);
		return ResponseEntity.ok(teacherModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Teacher>> updateTeacher(@PathVariable("id") int id,
			@RequestBody @Valid Teacher teacher) {
		service.update(teacher);
		EntityModel<Teacher> teacherModel = EntityModel.of(teacher);
		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GroupRestController.class).getGroupById(id))
				.withSelfRel();
		teacherModel.add(selfLink);
		return ResponseEntity.ok(teacherModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTeacher(@PathVariable("id") int id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
