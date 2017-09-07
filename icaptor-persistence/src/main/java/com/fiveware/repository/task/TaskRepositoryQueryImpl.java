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

import org.springframework.util.StringUtils;

import com.fiveware.model.Task;
import com.fiveware.repository.task.filter.TaskFilter;

public class TaskRepositoryQueryImpl implements TaskRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Task> filtrar(TaskFilter filter) {
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

		if (StringUtils.isEmpty(filter)) {
			//predicates.add(builder.);
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
