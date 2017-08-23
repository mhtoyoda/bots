package com.fiveware.model.activity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fiveware.model.Task;
import com.fiveware.model.user.IcaptorUser;

@Entity
@Table(name = "recent_activity")
public class RecentActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	private String description;

	@Column(name = "creation_time")
	private LocalDateTime creationTime;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Task task;

	@ManyToOne
	@JoinColumn(name = "task_id")
	private IcaptorUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public IcaptorUser getUser() {
		return user;
	}

	public void setUser(IcaptorUser user) {
		this.user = user;
	}

}
