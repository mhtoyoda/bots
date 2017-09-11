package com.fiveware.repository.task.filter;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class TaskFilter {

	private List<Long> taskStatusIds;

	private List<Long> botsIds;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate startedDate;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate finishedDate;

	private List<Long> usersIds;

	public List<Long> getTaskStatusIds() {
		return taskStatusIds;
	}

	public void setTaskStatusIds(List<Long> taskStatusIds) {
		this.taskStatusIds = taskStatusIds;
	}

	public List<Long> getBotsIds() {
		return botsIds;
	}

	public void setBotsIds(List<Long> botsIds) {
		this.botsIds = botsIds;
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

	public List<Long> getUsersIds() {
		return usersIds;
	}

	public void setUsersIds(List<Long> usersIds) {
		this.usersIds = usersIds;
	}

}