package com.foxminded.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.university.models.Group;

@Repository
public interface GroupDao extends JpaRepository<Group, Integer> {

}
