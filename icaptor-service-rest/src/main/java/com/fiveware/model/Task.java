package com.fiveware.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.pojomatic.Pojomatic;

import com.fiveware.model.user.IcaptorUser;

@Entity
@Table(name = "task")
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "load_time")
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDate loadTime;

	@Column(name = "start_at")
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDate startAt;

	@Column(name = "end_at")
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDate endAt;

	@Column(name = "schedule_to")
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDate scheduleTo;

	@ManyToOne
	@JoinColumn(name = "id_bot")
	private Bot bot;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private IcaptorUser usuario;

	@ManyToOne
	@JoinColumn(name = "id_status")
	private StatuProcessTask statusProcess;

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

	public LocalDate getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(LocalDate loadTime) {
		this.loadTime = loadTime;
	}

	public LocalDate getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDate startAt) {
		this.startAt = startAt;
	}

	public LocalDate getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDate endAt) {
		this.endAt = endAt;
	}

	public LocalDate getScheduleTo() {
		return scheduleTo;
	}

	public void setScheduleTo(LocalDate scheduleTo) {
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

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

}
