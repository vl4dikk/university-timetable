package com.foxminded.university.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.university.models.Audience;

@Repository
public interface AudienceDao extends JpaRepository<Audience, Integer> {

}
