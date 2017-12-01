package com.fiveware.service.parameter;

import com.fiveware.model.Parameter;
import com.fiveware.model.ScopeParameter;
import com.fiveware.model.TypeParameter;
import com.fiveware.model.user.IcaptorUser;
import com.fiveware.repository.ParameterRepository;
import com.fiveware.repository.ScopeParameterRepository;
import com.fiveware.repository.TypeParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ServiceParameterImpl {

    @Autowired
    private final ParameterRepository parameterRepository;

    @Autowired
    private final ScopeParameterRepository scopeParameterRepository;

    @Autowired
    private final TypeParameterRepository typeParameterRepository;

    public ServiceParameterImpl(ParameterRepository parameterRepository, ScopeParameterRepository scopeParameterRepository,
                                TypeParameterRepository typeParameterRepository) {
        this.parameterRepository = parameterRepository;
        this.scopeParameterRepository = scopeParameterRepository;
        this.typeParameterRepository = typeParameterRepository;
    }

    public Parameter findOne(Long id) {
        return parameterRepository.findOne(id);
    }

    public ScopeParameter findOneScope(Long id) {
        return scopeParameterRepository.findOne(id);
    }

    public Iterable<ScopeParameter> findAllScope() {
        return scopeParameterRepository.findAll();
    }

    public TypeParameter findOneType(Long id) {
        return typeParameterRepository.findOne(id);
    }

    public TypeParameter findByNameType(String name) {
        return typeParameterRepository.findByName(name);
    }

    public Iterable<TypeParameter> findAllType() {
        return typeParameterRepository.findAll();
    }

    public Parameter saveParameter(Parameter parameter, Long id) {
        if (!Objects.isNull(id))
            parameter.setUsuario(new IcaptorUser(id));

        parameter = parameterRepository.save(parameter);
        return parameter;
    }

    public List<Parameter> saveParameter(List<Parameter> parameters, Long id) {
        parameters.stream().forEach((param) -> saveParameter(param,id));

        return parameters;
    }

    public void delete(Long id) {
        parameterRepository.delete(id);
    }

    public void delete(List<Parameter> parameters) {
        parameters.forEach((parameterRepository::delete));
    }

    public TypeParameter save(TypeParameter typeParameter) {
        return typeParameterRepository.save(typeParameter);
    }

    public List<Parameter> findByBotName(String botName) {
        return parameterRepository.findByBotName(botName);
    }

    public List<Parameter> findParameterByScopeParameterNameAndTypeParameterName(String nameScope, String nameType) {
        return parameterRepository.findParameterByScopeParameterNameAndTypeParameterName(nameScope,nameType);
    }

    public List<Parameter> findParametersByBotNameAndUser(String botName, Long userId) {
        return parameterRepository.findParametersByBotNameAndUser(botName,userId);
    }

    public List<Parameter> findParametersByBotNameAndPriority(String botName, String scopeName) {
        return parameterRepository.findParametersByBotNameAndPriority(botName,scopeName);
    }

    public List<Parameter> findParameterByScopeParameterName(String scopeName) {

        return parameterRepository.findParameterByScopeParameterName(scopeName);
    }

    public Iterable<Parameter> findAllParametersById(Long id) {
        Iterable<Parameter> parameters = null;
        if (!Objects.isNull(id))
            parameters = parameterRepository.findByUsuarioId(id);
        else
            parameters = parameterRepository.findAll();
        return parameters;
    }

    public Iterable<Parameter> findAll() {
        return parameterRepository.findAll();
    }
}
