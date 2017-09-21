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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.pojomatic.Pojomatic;

import com.fiveware.model.user.IcaptorUser;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
@Entity
@Table(name = "task")
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "load_time")
	private LocalDateTime loadTime;


	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "start_at")
	private LocalDateTime startAt;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "end_at")
	private LocalDateTime endAt;

	@Column(name = "schedule_to")
	private LocalDateTime scheduleTo;

	@ManyToOne
	@JoinColumn(name = "id_bot")
	private Bot bot;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private IcaptorUser usuario;

	@ManyToOne
	@JoinColumn(name = "id_status")
	private StatuProcessTask statusProcess;


	@Transient
	private BotsMetric botsMetric;

	public Task() {
	}

	public Task(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(LocalDateTime loadTime) {
		this.loadTime = loadTime;
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

	public LocalDateTime getScheduleTo() {
		return scheduleTo;
	}

	public void setScheduleTo(LocalDateTime scheduleTo) {
		this.scheduleTo = scheduleTo;
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public IcaptorUser getUsuario() {
		return usuario;
	}

	public void setUsuario(IcaptorUser usuario) {
		this.usuario = usuario;
	}

	public StatuProcessTask getStatusProcess() {
		return statusProcess;
	}

	public void setStatusProcess(StatuProcessTask statusProcess) {
		this.statusProcess = statusProcess;
	}

	public BotsMetric getBotsMetric() {
		return botsMetric;
	}

	public void setBotsMetric(BotsMetric botsMetric) {this.botsMetric = botsMetric;}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

}
