package com.fiveware.repository.task;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.fiveware.model.Task;
import com.fiveware.repository.task.filter.TaskFilter;

public class TaskRepositoryImpl implements TaskRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Task> filter(TaskFilter filter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Task> criteria = builder.createQuery(Task.class);

		Root<Task> root = criteria.from(Task.class);

		Predicate[] predicates = createPredicates(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<Task> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] createPredicates(TaskFilter filter, CriteriaBuilder builder, Root<Task> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filter.getStartedDate() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("startAt"), filter.getStartedDate()));
		}

		if (filter.getFinishedDate() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("endAt"), filter.getFinishedDate()));
		}

		if (filter.getBotsIds() != null) {
			predicates.add(root.get("id_bot").in(filter.getBotsIds()));
		}

		if (filter.getTaskStatusIds() != null && !filter.getTaskStatusIds().isEmpty()) {
			predicates.add(root.get("id_status").in(filter.getTaskStatusIds()));
		}

		if (filter.getUsersIds() != null) {
			predicates.add(root.get("id_user").in(filter.getUsersIds()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}