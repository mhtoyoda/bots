package com.fiveware.repository.task;

import java.util.List;

import com.fiveware.model.Task;
import com.fiveware.repository.task.filter.TaskFilter;

public interface TaskRepositoryQuery {

	public List<Task> filtrar(TaskFilter filter);
}
