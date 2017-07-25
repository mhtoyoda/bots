package com.fiveware;

import com.fiveware.config.DataBaseConfigTest;
import com.fiveware.model.Agent;
import com.fiveware.repository.ServerRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= DataBaseConfigTest.class)
@Ignore
public class IcaptorPersistenceApplicationTests {


	@Autowired
	private ServerRepository serverRepository;

	@Test
	public void contextLoads() {

		List<Agent> server =
				serverRepository.findByAgentsNameAgent("server");

		System.out.println("server = " + server);
	}

}
