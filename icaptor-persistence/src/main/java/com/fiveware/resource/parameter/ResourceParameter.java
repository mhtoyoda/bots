package com.fiveware.resource.parameter;

import java.util.List;

import com.fiveware.service.parameter.ServiceParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.Parameter;
import com.fiveware.model.ScopeParameter;
import com.fiveware.model.TypeParameter;

@RestController
@RequestMapping("/api/parameter")
public class ResourceParameter {

	@Autowired
	private ServiceParameterImpl serviceParameter;

	@GetMapping("/{id}")
	public Parameter findParameterById(@PathVariable Long id) {
		return serviceParameter.findOne(id);
	}

	@GetMapping("/scope/{id}")
	public ScopeParameter findScopeParameterById(@PathVariable Long id) {
		return serviceParameter.findOneScope(id);
	}

	@GetMapping("/scope")
	public ResponseEntity<Iterable<ScopeParameter>> findScopeParameterAll() {
		return ResponseEntity.ok(serviceParameter.findAllScope());
	}

	@GetMapping("/type/{id}")
	public TypeParameter findTypeParameterById(@PathVariable Long id) {
		return serviceParameter.findOneType(id);
	}

	@GetMapping("/type/name/{name}")
	public TypeParameter findTypeParameterByName(@PathVariable String name) {
		return serviceParameter.findByNameType(name);
	}

	@GetMapping("/type")
	public ResponseEntity<Iterable<TypeParameter>> findTypeParameterAll() {
		return ResponseEntity.ok(serviceParameter.findAllType());
	}

	@PostMapping
	public ResponseEntity<Parameter> save(@RequestBody Parameter parameter, Long id) {

		parameter = serviceParameter.saveParameter(parameter, id);

		return ResponseEntity.status(HttpStatus.CREATED).body(parameter);
	}

	@PostMapping("/several")
	public ResponseEntity<Object> save(@RequestBody List<Parameter> parameters,Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(serviceParameter.saveParameter(parameters, id));
	}

	@PostMapping("/several/user/{id}")
	public ResponseEntity<Object> saveByUser(@RequestBody List<Parameter> parameters,@PathVariable Long id) {
		return save(parameters,id);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@RequestBody Long id) {
		serviceParameter.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/all")
	public ResponseEntity<Void> delete(@RequestBody List<Parameter> parameters) {
		serviceParameter.delete(parameters);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/type")
	public ResponseEntity<TypeParameter> save(@RequestBody TypeParameter typeParameter) {
		typeParameter = serviceParameter.save(typeParameter);
		return ResponseEntity.status(HttpStatus.CREATED).body(typeParameter);
	}

	@GetMapping("/bot/{botName}")
	public ResponseEntity<List<Parameter>> findTypeParameterByBotName(@PathVariable("botName") String botName) {
		return ResponseEntity.ok(serviceParameter.findByBotName(botName));
	}

	@GetMapping("/bot/{nameScope}/{nameType}")
	public ResponseEntity<List<Parameter>> findParameterByScopeAndType(@PathVariable("nameScope") String nameScope,
																	   @PathVariable("nameType") String nameType) {
		return ResponseEntity.ok(serviceParameter.findParameterByScopeParameterNameAndTypeParameterName(nameScope, nameType));
	}

	@GetMapping("/bot-user/{botName}/{userId}")
	public ResponseEntity<List<Parameter>> findParametersByBotAndUser(@PathVariable("botName") String botName,
																	  @PathVariable("userId") Long userId) {
		List<Parameter> parameters = serviceParameter.findParametersByBotNameAndUser(botName, userId);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping("/bot-scope/{botName}/{scopeName}")
	public ResponseEntity<List<Parameter>> findParametersByBotAndScope(@PathVariable("botName") String botName, @PathVariable("scopeName") String scopeName) {
		List<Parameter> parameters = serviceParameter.findParametersByBotNameAndPriority(botName, scopeName);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping("/scope-parameter/{scopeName}")
	public ResponseEntity<List<Parameter>> listParametersByScope(@PathVariable("scopeName") String scopeName) {
		List<Parameter> parameters = serviceParameter.findParameterByScopeParameterName(scopeName);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping({"/","/user/{id}"})
	public ResponseEntity<Iterable<Parameter>> findAll(@PathVariable Long id) {
		Iterable<Parameter> parameters = serviceParameter.findAllParametersById(id);

		return ResponseEntity.ok(parameters);
	}

	@GetMapping
	public ResponseEntity<Iterable<Parameter>> findAll() {
		Iterable<Parameter> parameters =  serviceParameter.findAll();

		return ResponseEntity.ok(parameters);
	}
}
