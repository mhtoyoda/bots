package com.fiveware;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fiveware.config.DataBaseConfigTest;
import com.fiveware.repository.ServerRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= DataBaseConfigTest.class)
@Ignore
public class IcaptorPersistenceApplicationTests {


	@Autowired
	private ServerRepository serverRepository;

	@Test
	public void contextLoads() {

//		Set<Agent> server =
//				serverRepository.findByName("server").get().getAgents();
//
//		System.out.println("server = " + server);
	}

}
