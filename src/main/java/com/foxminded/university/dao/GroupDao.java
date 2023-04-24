package com.foxminded.university.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.foxminded.university.exceptions.DAOException;
import com.foxminded.university.models.Group;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class GroupDao {

	private static final Logger logger = LoggerFactory.getLogger(GroupDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void insert(Group group) {
		if (group == null) {
			String error = "Cannot insert group, because its null";
			logger.error(error);
			throw new DAOException(error);
		}
		logger.trace("Start inserting group with name {}", group.getName());
		entityManager.persist(group);
		logger.debug("Group with name {} inserted", group.getName());
	}

	@Transactional
	public void deleteById(int groupId) {
		logger.trace("Deleting group with id {}", groupId);
		Group group = entityManager.find(Group.class, groupId);
		if (group != null) {
			entityManager.remove(group);
			logger.trace("Group with id {} was deleted", groupId);
		}
	}

	public List<Group> getAllGroups() {
		logger.trace("Getting all groups");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Group> cq = cb.createQuery(Group.class);
		Root<Group> root = cq.from(Group.class);
		cq.select(root);
		TypedQuery<Group> query = entityManager.createQuery(cq);
		return query.getResultList();
	}

	public Group getGroupById(int groupId) {
		logger.trace("Getting group with id {}", groupId);
		Group group;
		try {
			group = entityManager.find(Group.class, groupId);
		} catch (EmptyResultDataAccessException exception) {
			String error = String.format("Cannot find group with id '%s'", groupId);
			logger.error(error);
			throw new DAOException(error, exception);
		} catch (DataAccessException exception) {
			String error = String.format("Unable to get group with ID '%s'", groupId);
			logger.error(error);
			throw new DAOException(error, exception);
		}
		return group;
	}

	@Transactional
	public void update(Group group) {
		logger.trace("Updating group with id {}", group.getId());
		entityManager.merge(group);
	}

}
