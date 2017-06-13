package com.fiveware.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "server_agent")
public class ServerAgent {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
}
