package com.fiveware.model.activity;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fiveware.model.Bot;
import com.fiveware.model.Task;
import com.fiveware.model.user.IcaptorUser;

@Entity
@Table(name = "recent_activity")
public class RecentActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50)
	private String message;

	@Column(name = "creation_time")
	private LocalDateTime creationTime;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private IcaptorUser user;

	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;

	@ManyToOne
	@JoinColumn(name = "bot_id")
	private Bot bot;

	private Boolean visualized = new Boolean(false);

	public RecentActivity(String message, Long user) {
		this.message = message;
		this.user = new IcaptorUser();
		this.user.setId(user);
		this.creationTime = LocalDateTime.now();
	}

	public RecentActivity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public IcaptorUser getUser() {
		return user;
	}

	public void setUser(IcaptorUser user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	public Bot getBot() {
		return bot;
	}
	
	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public Boolean getVisualized() {
		return visualized;
	}

	public void setVisualized(Boolean visualized) {
		this.visualized = visualized;
	}

	@Override
	public String toString() {
		return "RecentActivity [id=" + id + ", message=" + message + ", creationTime=" + creationTime + ", user=" + user + ", task=" + task + ", bot=" + bot + ", visualized="
				+ visualized + "]";
	}

}
