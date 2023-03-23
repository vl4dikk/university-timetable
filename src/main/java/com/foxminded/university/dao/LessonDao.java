package com.foxminded.university.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.foxminded.university.exceptions.DAOException;
import com.foxminded.university.models.Audience;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lesson;
import com.foxminded.university.models.Teacher;

@Repository
public class LessonDao {

	private static final Logger logger = LoggerFactory.getLogger(LessonDao.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public LessonDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(Lesson lesson) {
		logger.trace("Start inserting lesson");
		if (lesson == null) {
			String error = "Cannot insert lesson, because its null";
			logger.error(error);
			throw new DAOException(error);
		}
		String sql = "INSERT INTO lessons (name, teacher_id, group_id, audience_id, lTime) VALUES (?, ?, ?, ?, ?)";
		Timestamp timeStamp = Timestamp.valueOf(lesson.getTime());
		jdbcTemplate.update(sql, lesson.getName(), lesson.getTeacher().getTeacherId(), lesson.getGroup().getId(),
				lesson.getAudience().getAudienceId(), timeStamp);
	}

	public void deleteById(int lessonId) {
		logger.trace("Deleting lesson with id {}", lessonId);
		String sql = "DELETE FROM lessons WHERE lessonId = ?";
		jdbcTemplate.update(sql, lessonId);
	}

	public List<Lesson> getAllLessons() {
		logger.trace("Getting all lessons");
		String sql = "SELECT l.lessonId, l.name, l.teacher_id, t.firstName AS teacher_firstName, t.lastName AS teacher_lastName, g.id AS group_id, g.name AS group_name, a.audienceId AS audience_id, a.audienceNumber AS audience_number, l.lTime "
				+ "FROM lessons l " + "JOIN teachers t ON l.teacher_id = t.teacherId "
				+ "JOIN groups g ON l.group_id = g.id " + "JOIN audiences a ON l.audience_id = a.audienceId";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			Lesson lesson = new Lesson();
			Teacher teacher = new Teacher();
			Group group = new Group();
			Audience audience = new Audience();
			group.setId(rs.getInt("group_id"));
			group.setName(rs.getString("group_name"));
			teacher.setTeacherId(rs.getInt("teacher_id"));
			teacher.setFirstName(rs.getString("teacher_firstName"));
			teacher.setLastName(rs.getString("teacher_lastName"));
			audience.setAudienceId(rs.getInt("audience_id"));
			audience.setAudienceNumber(rs.getInt("audience_number"));
			lesson.setTeacher(teacher);
			lesson.setGroup(group);
			lesson.setAudience(audience);
			lesson.setLessonId(rs.getInt("lessonId"));
			lesson.setName(rs.getString("name"));
			LocalDateTime time = rs.getObject("lTime", LocalDateTime.class);
			lesson.setTime(time);
			return lesson;
		});
	}

	public Lesson getById(int lessonId) {
		logger.trace("Getting lesson with id {}", lessonId);
		Lesson result;
		String sql = "SELECT l.lessonId, l.name, l.teacher_id, t.firstName AS teacher_firstName, t.lastName AS teacher_lastName, g.id AS group_id, g.name AS group_name, a.audienceId AS audience_id, a.audienceNumber AS audience_number, l.lTime "
				+ "FROM lessons l " + "JOIN teachers t ON l.teacher_id = t.teacherId "
				+ "JOIN groups g ON l.group_id = g.id " + "JOIN audiences a ON l.audience_id = a.audienceId "
				+ "WHERE l.lessonId = ?";
		RowMapper<Lesson> rowMapper = (rs, rowNum) -> {
			Lesson lesson = new Lesson();
			Teacher teacher = new Teacher();
			Group group = new Group();
			Audience audience = new Audience();
			group.setId(rs.getInt("group_id"));
			group.setName(rs.getString("group_name"));
			teacher.setTeacherId(rs.getInt("teacher_id"));
			teacher.setFirstName(rs.getString("teacher_firstName"));
			teacher.setLastName(rs.getString("teacher_lastName"));
			audience.setAudienceId(rs.getInt("audience_id"));
			audience.setAudienceNumber(rs.getInt("audience_number"));
			lesson.setTeacher(teacher);
			lesson.setGroup(group);
			lesson.setAudience(audience);
			lesson.setLessonId(rs.getInt("lessonId"));
			lesson.setName(rs.getString("name"));
			LocalDateTime time = rs.getObject("lTime", LocalDateTime.class);
			lesson.setTime(time);
			return lesson;
		};
		try {
			result = jdbcTemplate.queryForObject(sql, rowMapper, lessonId);
		} catch (EmptyResultDataAccessException exception) {
			String error = String.format("Cannot find lesson with id '%s'", lessonId);
			logger.error(error);
			throw new DAOException(error, exception);
		} catch (DataAccessException exception) {
			String error = String.format("Unable to get lesson with ID '%s'", lessonId);
			logger.error(error);
			throw new DAOException(error, exception);
		}
		return result;
	}

}
