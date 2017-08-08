package com.fiveware.resource.parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.Parameter;
import com.fiveware.model.ScopeParameter;
import com.fiveware.model.TypeParameter;
import com.fiveware.repository.ParameterRepository;
import com.fiveware.repository.ScopeParameterRepository;
import com.fiveware.repository.TypeParameterRepository;

@RestController
@RequestMapping("/api/parameter")
public class ResourceParameter {

	@Autowired
	private ParameterRepository parameterRepository;
	
	@Autowired
	private ScopeParameterRepository scopeParameterRepository;
	
	@Autowired
	private TypeParameterRepository typeParameterRepository;
	
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
		parameter = parameterRepository.save(parameter);
		return ResponseEntity.status(HttpStatus.CREATED).body(parameter);
	}
}
