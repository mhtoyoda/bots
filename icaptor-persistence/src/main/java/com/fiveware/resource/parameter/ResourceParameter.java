package com.fiveware.resource.parameter;

import com.fiveware.model.Parameter;
import com.fiveware.model.ScopeParameter;
import com.fiveware.model.TypeParameter;
import com.fiveware.repository.ParameterRepository;
import com.fiveware.repository.Parameters;
import com.fiveware.repository.ScopeParameterRepository;
import com.fiveware.repository.TypeParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parameter")
public class ResourceParameter {

	@Autowired
	private ParameterRepository parameterRepository;

	@Autowired
	private Parameters parametersMerge;

	@Autowired
	private ScopeParameterRepository scopeParameterRepository;

	@Autowired
	private TypeParameterRepository typeParameterRepository;

	@GetMapping("/{id}")
	public Parameter findParameterById(@PathVariable Long id) {
		return parameterRepository.findOne(id);
	}

	@GetMapping("/scope/{id}")
	public ScopeParameter findScopeParameterById(@PathVariable Long id) {
		return scopeParameterRepository.findOne(id);
	}

	@GetMapping("/scope")
	public ResponseEntity<Iterable<ScopeParameter>> findScopeParameterAll() {
		return ResponseEntity.ok(scopeParameterRepository.findAll());
	}

	@GetMapping("/type/{id}")
	public TypeParameter findTypeParameterById(@PathVariable Long id) {
		return typeParameterRepository.findOne(id);
	}

	@GetMapping("/type/name/{name}")
	public TypeParameter findTypeParameterByName(@PathVariable String name) {
		return typeParameterRepository.findByName(name);
	}

	@GetMapping("/type")
	public ResponseEntity<Iterable<TypeParameter>> findTypeParameterAll() {
		return ResponseEntity.ok(typeParameterRepository.findAll());
	}

	@PostMapping
	public ResponseEntity<Parameter> save(@RequestBody Parameter parameter) {
		final TypeParameter[] type = {parameter.getTypeParameter()};
		Optional<Parameter> parameterOptional = Optional.ofNullable(parameterRepository.
				findParameterByTypeParameterNameAndScopeParameterName(parameter.getTypeParameter().getName(),
																	  parameter.getScopeParameter().getName()));
		parameterOptional.ifPresent( (param) ->{
				type[0] = param.getTypeParameter();
		});
		parameter.setTypeParameter(type[0]);
		if (parameter.getTypeParameter().isNew())
			parameter = parameterRepository.save(parameter);
		else
			parameter = parametersMerge.save(parameter);

		return ResponseEntity.status(HttpStatus.CREATED).body(parameter);
	}
	
	@PostMapping("/several")
	public ResponseEntity<Object> save(@RequestBody List<Parameter> parameters) {
		parameters.stream().forEach((param) -> save(param));
		return ResponseEntity.status(HttpStatus.CREATED).body(parameters);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@RequestBody Long id) {
		parameterRepository.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@DeleteMapping("/all")
	public ResponseEntity<Void> delete(@RequestBody List<Parameter> parameters) {
		parameterRepository.delete(parameters);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/type")
	public ResponseEntity<TypeParameter> save(@RequestBody TypeParameter typeParameter) {
		typeParameter = typeParameterRepository.save(typeParameter);
		return ResponseEntity.status(HttpStatus.CREATED).body(typeParameter);
	}

	@GetMapping("/bot/{botName}")
	public ResponseEntity<Iterable<Parameter>> findTypeParameterByBotName(@PathVariable("botName") String botName) {
		return ResponseEntity.ok(parameterRepository.findByBotName(botName));
	}

	@GetMapping("/bot/{nameScope}/{nameType}")
	public ResponseEntity<List<Parameter>> findParameterByScopeAndType(@PathVariable("nameScope") String nameScope, @PathVariable("nameType") String nameType) {
		return ResponseEntity.ok(parameterRepository.findParameterByScopeParameterNameAndTypeParameterName(nameScope, nameType));
	}

	@GetMapping("/bot-user/{botName}/{userId}")
	public ResponseEntity<List<Parameter>> findParametersByBotAndUser(@PathVariable("botName") String botName, @PathVariable("userId") Long userId) {
		List<Parameter> parameters = parameterRepository.findParametersByBotNameAndUser(botName, userId);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping("/bot-scope/{botName}/{scopeName}")
	public ResponseEntity<List<Parameter>> findParametersByBotAndScope(@PathVariable("botName") String botName, @PathVariable("scopeName") String scopeName) {
		List<Parameter> parameters = parameterRepository.findParametersByBotNameAndPriority(botName, scopeName);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping("/scope-parameter/{scopeName}")
	public ResponseEntity<List<Parameter>> listParametersByScope(@PathVariable("scopeName") String scopeName) {
		List<Parameter> parameters = parameterRepository.findParameterByScopeParameterName(scopeName);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping
	public ResponseEntity<Iterable<Parameter>> findAll() {
		Iterable<Parameter> parameters = parameterRepository.findAll();
		return ResponseEntity.ok(parameters);
	}
}
