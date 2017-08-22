package com.fiveware.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class AgentController {

	private static final Logger logger = LoggerFactory.getLogger(AgentController.class);
	
	@GetMapping("/loadAgentInfo")
	@PreAuthorize("hasAuthority('ROLE_AGENT_LIST') and #oauth2.hasScope('write')")
	public ResponseEntity<?> loadAgentInfo() {
		logger.info("Carregando as informacoes dos agents.");
		return ResponseEntity.ok().build();
	}
	
}
