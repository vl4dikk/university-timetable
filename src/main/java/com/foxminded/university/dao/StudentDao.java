package com.foxminded.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.university.models.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {

}
