package com.fiveware.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
@Entity
@Table(name = "item_task")
public class ItemTask implements Log, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "start_at")
	private LocalDateTime startAt;

	@Column(name = "end_at")
	private LocalDateTime endAt;

	@Column(name = "attempts_count")
	private Integer attemptsCount;

	@ManyToOne
	@JoinColumn(name = "id_task")
	private Task task;

	@ManyToOne
	@JoinColumn(name = "id_status")
	private StatusProcessItemTask statusProcess;

	@Column(name = "data_in")
	private String dataIn;

	@Column(name = "data_out")
	private String dataOut;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}

	public Integer getAttemptsCount() {
		return attemptsCount;
	}

	public void setAttemptsCount(Integer attemptsCount) {
		this.attemptsCount = attemptsCount;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public StatusProcessItemTask getStatusProcess() {
		return statusProcess;
	}

	public void setStatusProcess(StatusProcessItemTask statusProcess) {
		this.statusProcess = statusProcess;
	}

	public String getDataIn() {
		return dataIn;
	}

	public void setDataIn(String dataIn) {
		this.dataIn = dataIn;
	}

	public String getDataOut() {
		return dataOut;
	}

	public void setDataOut(String dataOut) {
		this.dataOut = dataOut;
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

}
