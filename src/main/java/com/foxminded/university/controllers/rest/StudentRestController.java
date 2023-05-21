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

import com.foxminded.university.models.Student;
import com.foxminded.university.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

	@Autowired
	private StudentService service;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Student>>> getAllStudents() {
		List<Student> students = service.getAllStudents();
		List<EntityModel<Student>> studentModels = new ArrayList<EntityModel<Student>>();
		for (Student student : students) {
			EntityModel<Student> studentModel = EntityModel.of(student);
			Link selfLink = WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(StudentRestController.class).getStudentById(student.getStudentId()))
					.withSelfRel();
			studentModel.add(selfLink);
			studentModels.add(studentModel);
		}
		CollectionModel<EntityModel<Student>> collectionModel = CollectionModel.of(studentModels);
		return ResponseEntity.ok(collectionModel);
	}

	@PostMapping
	public ResponseEntity<Void> addStudent(@RequestBody @Valid Student student) {
		service.insert(student);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Student>> getStudentById(@PathVariable("id") int id) {
		Student student = service.getStudentById(id);
		if (student == null) {
			return ResponseEntity.notFound().build();
		}
		EntityModel<Student> studentModel = EntityModel.of(student);
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(StudentRestController.class).getStudentById(id)).withSelfRel();
		studentModel.add(selfLink);
		return ResponseEntity.ok(studentModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Student>> updateStudent(@PathVariable("id") int id,
			@RequestBody @Valid Student student) {
		service.update(student);
		EntityModel<Student> studentModel = EntityModel.of(student);
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(StudentRestController.class).getStudentById(id)).withSelfRel();
		studentModel.add(selfLink);
		return ResponseEntity.ok(studentModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("id") int id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
