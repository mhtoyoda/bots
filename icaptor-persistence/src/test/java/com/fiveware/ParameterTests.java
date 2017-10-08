package com.fiveware;

import com.fiveware.model.Parameter;
import com.fiveware.model.ScopeParameter;
import com.fiveware.model.TypeParameter;
import com.fiveware.repository.ParameterRepository;
import com.fiveware.repository.Parameters;
import com.fiveware.repository.user.UserRepository;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;


@ContextConfiguration(classes={DataBaseConfigTest.class})
@ActiveProfiles("test")
@Transactional
@Rollback(true)
@RunWith(SpringJUnit4ClassRunner.class)
public class ParameterTests {

//	@LocalServerPort
//	private int port;
//
//	RequestSpecification persistence = null;
//
//	@Before
//	public void setup(){
//		persistence = given().port(port);
//	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Parameters parametersDB;

	@Autowired
	private ParameterRepository parameterRepository;

	@Test
	public void saveOneParameter() {

		ScopeParameter scopeParameter = new ScopeParameter();
		scopeParameter.setId(3L);
		scopeParameter.setName("cloud_bot");
		scopeParameter.setPriority(1);

		TypeParameter typeParameter = new TypeParameter();
		typeParameter.setName("outro");


		Parameter parameter = new Parameter();
		parameter.setActive(true);
		parameter.setScopeParameter(scopeParameter);
		parameter.setTypeParameter(typeParameter);
		parameter.setFieldValue("testando");

		parameterRepository.save(parameter);

	}

	@Test
	public void saveListParameters() {

		List<Parameter> parameters = Lists.newArrayList();

		ScopeParameter scopeParameter = new ScopeParameter();
		scopeParameter.setId(3L);
		scopeParameter.setName("cloud_bot");
		scopeParameter.setPriority(1);

		TypeParameter email = new TypeParameter();
		email.setName("email");

		Parameter valueEmail = new Parameter();
		valueEmail.setActive(true);
		valueEmail.setScopeParameter(scopeParameter);
		valueEmail.setTypeParameter(email);
		valueEmail.setFieldValue("valdisnei.fajardo@icaptor.com.br");

		parameters.add(valueEmail);

		TypeParameter outroEmail = new TypeParameter();
		outroEmail.setName("outro");

		Parameter outroEmailValue = new Parameter();
		outroEmailValue.setActive(true);
		outroEmailValue.setScopeParameter(scopeParameter);
		outroEmailValue.setTypeParameter(outroEmail);
		outroEmailValue.setFieldValue("testando");

		parameters.add(outroEmailValue);


		parameterRepository.save(parameters);

	}


	@Test
	public void saveOneParameterBytTypeParameter() {

		ScopeParameter scopeParameter = new ScopeParameter();
		scopeParameter.setId(3L);
		scopeParameter.setName("cloud_bot");
		scopeParameter.setPriority(1);

		TypeParameter typeParameter = new TypeParameter();
		typeParameter.setId(2L);
		typeParameter.setName("retry");
		typeParameter.setCredential(false);
		typeParameter.setExclusive(false);

		Parameter parameter = new Parameter();
		parameter.setActive(true);
		parameter.setScopeParameter(scopeParameter);
		parameter.setTypeParameter(typeParameter);
		parameter.setFieldValue("testando");

		parametersDB.save(parameter);

//		List<Parameter> parameters = parameterRepository.findParameterByScopeParameterNameAndTypeParameterName("cloud_bot", "retry");
//		parameters.forEach((parameter1 -> {
//			System.out.println("parameter1 = " + parameter1);
//		}));


	}



}
