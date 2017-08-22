package com.fiveware.model.activity;

import java.time.LocalDate;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(max = 50)
	private String description;

	@Column(name = "creation_time")
	private LocalDate creationTime;

	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private Task task;

	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false)
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

	public LocalDate getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDate creationTime) {
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
