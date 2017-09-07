package com.fiveware.repository.task.filter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class TaskFilter {

	private List<Long> taskStatusIds;

	private List<String> botsNames;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate startedDate;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate finishedDate;

	private Long userId;

	public List<Long> getTaskStatusIds() {
		return taskStatusIds;
	}

	public void setTaskStatusIds(List<Long> taskStatusIds) {
		this.taskStatusIds = taskStatusIds;
	}

	public List<String> getBotsNames() {
		return botsNames;
	}

	public void setBotsNames(List<String> botsNames) {
		this.botsNames = botsNames;
	}

	public LocalDate getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(LocalDate startedDate) {
		this.startedDate = startedDate;
	}

	public LocalDate getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(LocalDate finishedDate) {
		this.finishedDate = finishedDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}