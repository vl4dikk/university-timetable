package com.foxminded.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.university.models.Teacher;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, Integer> {

}
