package com.fiveware.service.task;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fiveware.model.Task;

public interface IServiceTask {

	ResponseEntity<?> save(Task task);

	Task findOne(Long id);

	List<Task> getAllTaskByStatus(String nameBot, String status);

}
