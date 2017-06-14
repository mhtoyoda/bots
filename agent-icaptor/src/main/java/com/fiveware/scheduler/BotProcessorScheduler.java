package com.fiveware.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.loader.LoadFile;

@Component
public class BotProcessorScheduler {

	@Autowired
	private LoadFile loadFile;
	
	public void process(){
		
	}
}
