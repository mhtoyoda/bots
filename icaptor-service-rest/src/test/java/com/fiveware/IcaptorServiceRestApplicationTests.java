package com.fiveware;

import com.fiveware.service.ServiceCache;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class IcaptorServiceRestApplicationTests {

	@Autowired
	private ServiceCache serviceCache;


	@Test
	public void contextLoads() {

		serviceCache.add("usuario","valdis");
		serviceCache.add("senha","123456");

		Set list = serviceCache.list();
		System.out.println("list = " + list);


		// Remove
		serviceCache.remove("senha","123456");
		list = serviceCache.list();
		System.out.println("list = " + list);


	}

}
