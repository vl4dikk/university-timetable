package com.foxminded.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.university.models.Lesson;

@Repository
public interface LessonDao extends JpaRepository<Lesson, Integer> {

}
