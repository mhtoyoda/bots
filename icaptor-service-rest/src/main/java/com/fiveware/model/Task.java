package com.fiveware.model;

import java.io.Serializable;
import java.util.Date;

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

@Entity
@Table(name = "task")
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "load_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date loadTime;

	@Column(name = "start_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startAt;

	@Column(name = "end_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endAt;

	@Column(name = "schedule_to")
	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduleTo;

	@ManyToOne
	@JoinColumn(name = "id_bot")
	private Bot bot;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_status")
	private StatuProcessTask statusProcess;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public Date getScheduleTo() {
		return scheduleTo;
	}

	public void setScheduleTo(Date scheduleTo) {
		this.scheduleTo = scheduleTo;
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
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
