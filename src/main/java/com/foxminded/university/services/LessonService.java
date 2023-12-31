package com.foxminded.university.services;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.university.dao.LessonDao;
import com.foxminded.university.models.Audience;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lesson;
import com.foxminded.university.models.Teacher;

@Service
public class LessonService {

	private static final Logger logger = LoggerFactory.getLogger(LessonService.class);

	private LessonDao dao;

	@Autowired
	public LessonService(LessonDao dao) {
		this.dao = dao;
	}

	public void insert(String name, int teacherId, int groupId, int audienceId, LocalDateTime time) {
		logger.info("Started LessonService to insert lesson");
		Lesson lesson = new Lesson();
		lesson.setName(name);
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		lesson.setTeacher(teacher);
		Group group = new Group();
		group.setId(groupId);
		lesson.setGroup(group);
		Audience audience = new Audience();
		audience.setAudienceId(audienceId);
		lesson.setAudience(audience);
		lesson.setTime(time);
		dao.save(lesson);
		logger.info("Lesson inserted");
	}

	public void deleteById(int lessonId) {
		logger.info("Started LessonService to delete lesson by id");
		dao.deleteById(lessonId);
	}

	public List<Lesson> getAllLessons() {
		logger.info("Started LessonService to get all lessons");
		return dao.findAll();
	}

	public Lesson getById(int lessonId) {
		logger.info("Started LessonService to get lesson by id");
		return dao.getReferenceById(lessonId);
	}

	public void update(Lesson lesson) {
		dao.save(lesson);
	}
}
