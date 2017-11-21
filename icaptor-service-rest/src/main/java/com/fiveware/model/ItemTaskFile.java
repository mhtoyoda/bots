package com.fiveware.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
@Entity
@Table(name = "item_task_file")
public class ItemTaskFile implements Log, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(name = "item_file")
	private byte[] file;

	@ManyToOne
	@JoinColumn(name = "id_item_task")
	private ItemTask itemTask;

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public ItemTask getItemTask() {
		return itemTask;
	}

	public void setItemTask(ItemTask itemTask) {
		this.itemTask = itemTask;
	}

}
