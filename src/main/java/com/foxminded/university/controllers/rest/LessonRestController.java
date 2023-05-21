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

import com.foxminded.university.models.Lesson;
import com.foxminded.university.services.LessonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/lessons")
public class LessonRestController {

	@Autowired
	private LessonService service;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Lesson>>> getAllLessons() {
		List<Lesson> lessons = service.getAllLessons();
		List<EntityModel<Lesson>> lessonModels = new ArrayList<EntityModel<Lesson>>();
		for (Lesson lesson : lessons) {
			EntityModel<Lesson> lessonModel = EntityModel.of(lesson);
			Link selfLink = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(LessonRestController.class).getLessonById(lesson.getLessonId()))
					.withSelfRel();
			lessonModel.add(selfLink);
			lessonModels.add(lessonModel);
		}
		CollectionModel<EntityModel<Lesson>> collectionModel = CollectionModel.of(lessonModels);
		return ResponseEntity.ok(collectionModel);
	}

	@PostMapping
	public ResponseEntity<Void> addLesson(@RequestBody @Valid Lesson lesson) {
		service.insert(lesson.getName(), lesson.getTeacher().getTeacherId(), lesson.getGroup().getId(),
				lesson.getAudience().getAudienceId(), lesson.getTime());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Lesson>> getLessonById(@PathVariable("id") int id) {
		Lesson lesson = service.getById(id);
		if (lesson == null) {
			return ResponseEntity.notFound().build();
		}
		EntityModel<Lesson> lessonModel = EntityModel.of(lesson);
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(LessonRestController.class).getLessonById(id)).withSelfRel();
		lessonModel.add(selfLink);
		return ResponseEntity.ok(lessonModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Lesson>> updateLesson(@PathVariable("id") int id,
			@RequestBody @Valid Lesson lesson) {
		service.update(lesson);
		EntityModel<Lesson> lessonModel = EntityModel.of(lesson);
		Link selfLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(LessonRestController.class).getLessonById(id)).withSelfRel();
		lessonModel.add(selfLink);
		return ResponseEntity.ok(lessonModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLesson(@PathVariable("id") int id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
