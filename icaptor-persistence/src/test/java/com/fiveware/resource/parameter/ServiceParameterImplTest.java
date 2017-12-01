package com.fiveware.resource.parameter;

import com.fiveware.model.Parameter;
import com.fiveware.model.ScopeParameter;
import com.fiveware.model.TypeParameter;
import com.fiveware.repository.ParameterRepository;
import com.fiveware.repository.ScopeParameterRepository;
import com.fiveware.repository.TypeParameterRepository;
import com.fiveware.service.parameter.ServiceParameterImpl;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@Service
public class ServiceParameterImplTest {

    @Mock
    ParameterRepository parameterRepository;

    @Mock
    ScopeParameterRepository scopeParameterRepository;

    @Mock
    TypeParameterRepository typeParameterRepository;

    ServiceParameterImpl serviceParameter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.serviceParameter = new ServiceParameterImpl(parameterRepository, scopeParameterRepository,
                typeParameterRepository);
    }

    @Test
    public void findOne() {
        Parameter paramter = new Parameter();
        when(parameterRepository.findOne(anyLong())).thenReturn(paramter);

        assertNotNull(serviceParameter.findOne(anyLong()));

        verify(parameterRepository, times(1)).findOne(anyLong());
    }

    @Test
    public void findOneScope() {
        ScopeParameter scopeParameter = new ScopeParameter();
        when(scopeParameterRepository.findOne(anyLong())).thenReturn(scopeParameter);

        assertNotNull(serviceParameter.findOneScope(anyLong()));

        verify(scopeParameterRepository, times(1)).findOne(anyLong());
    }

    @Test
    public void findAllScope() {
        ScopeParameter scopeparameter = new ScopeParameter();
        Iterable<ScopeParameter> listScope = Lists.newArrayList(scopeparameter);
        when(scopeParameterRepository.findAll()).thenReturn(listScope);

        assertNotNull(serviceParameter.findAllScope().iterator().next());

        verify(scopeParameterRepository).findAll();
    }

    @Test
    public void findOneType() throws Exception {
        TypeParameter typeParameter=new TypeParameter();
        when(typeParameterRepository.findOne(anyLong())).thenReturn(typeParameter);

        assertNotNull(serviceParameter.findOneType(anyLong()));

        verify(typeParameterRepository).findOne(anyLong());
    }

    @Test
    public void findByNameType() throws Exception {
        TypeParameter typeParameter=new TypeParameter();
        when(typeParameterRepository.findByName(anyString())).thenReturn(typeParameter);

        assertNotNull(serviceParameter.findByNameType(anyString()));
        verify(typeParameterRepository).findByName(anyString());
    }

    @Test
    public void findAllType() throws Exception {
        TypeParameter typeParameter=new TypeParameter();
        Iterable<TypeParameter> listType = Lists.newArrayList(typeParameter);

        when(typeParameterRepository.findAll()).thenReturn(listType);

        assertNotNull(serviceParameter.findAllType().iterator().next());
        verify(typeParameterRepository).findAll();
    }

    @Test
    public void saveParameter() throws Exception {
        Parameter parameter=new Parameter();
        when(parameterRepository.save(parameter)).thenReturn(parameter);

        assertNotNull(serviceParameter.saveParameter(parameter, anyLong()));
        assertNotNull(parameter.getUsuario());

        verify(parameterRepository).save(parameter);

    }

    @Test
    public void saveParameterList() throws Exception {
        Parameter parameter=new Parameter();

        Long id = 1L;
        ServiceParameterImpl spyServiceParamter = spy(serviceParameter);
        when(spyServiceParamter.saveParameter(parameter, id)).thenReturn(parameter);

        List<Parameter> parameters=Lists.newArrayList(parameter,parameter);

        assertEquals(serviceParameter.saveParameter(parameters,id).size(),2);
        assertNotNull(parameter.getUsuario());

        verify(parameterRepository,times(2)).save(parameter);
        verify(spyServiceParamter,times(1)).saveParameter(parameter, id);
    }

    @Test
    public void delete() throws Exception {

        serviceParameter.delete(anyLong());
        verify(parameterRepository).delete(anyLong());
    }

    @Test
    public void deleteList() throws Exception {
        Parameter parameter=new Parameter();
        List<Parameter> parameters=Lists.newArrayList(parameter,parameter,parameter);

        serviceParameter.delete(parameters);

        verify(parameterRepository,times(3)).delete(parameter);
    }

    @Test
    public void save() throws Exception {
        TypeParameter typeParameter=new TypeParameter();

        when(typeParameterRepository.save(typeParameter)).thenReturn(typeParameter);

        assertNotNull(serviceParameter.save(typeParameter));

        verify(typeParameterRepository,times(1)).save(typeParameter);
    }

    @Test
    public void findByBotName() throws Exception {
        Parameter parameter=new Parameter();
        List<Parameter> parameters=Lists.newArrayList(parameter,parameter,parameter);

        when(parameterRepository.findByBotName(anyString())).thenReturn(parameters);

        assertEquals(serviceParameter.findByBotName(anyString()).size(),3);

        verify(parameterRepository,times(1)).findByBotName(anyString());
    }

    @Test
    public void findParameterByScopeParameterNameAndTypeParameterName() throws Exception {
        //@formatter:on
        Parameter parameter=new Parameter();
        List<Parameter> parameters= Lists.newArrayList(parameter);
        when(parameterRepository.
                findParameterByScopeParameterNameAndTypeParameterName(anyString(),anyString())).
                thenReturn(parameters);

        assertEquals(serviceParameter.
                findParameterByScopeParameterNameAndTypeParameterName(anyString(),anyString()).size(),1);

        verify(parameterRepository,times(1)).
                findParameterByScopeParameterNameAndTypeParameterName(anyString(),anyString());

        //@formatter:off
    }

    @Test
    public void findParametersByBotNameAndUser() throws Exception {
        Parameter parameter=new Parameter();
        List<Parameter> parameters= Lists.newArrayList(parameter,parameter,parameter,parameter);
        when(parameterRepository.findParametersByBotNameAndUser(anyString(),anyLong())).thenReturn(parameters);

        assertEquals(serviceParameter.findParametersByBotNameAndUser(anyString(),anyLong()).size(),4);

        verify(parameterRepository,times(1)).findParametersByBotNameAndUser(anyString(),anyLong());
    }

    @Test
    public void findParametersByBotNameAndPriority() throws Exception {
        Parameter parameter=new Parameter();
        List<Parameter> parameters=Lists.newArrayList(parameter,parameter,parameter);

        when(parameterRepository.findParametersByBotNameAndPriority(anyString(),anyString())).thenReturn(parameters);

        assertEquals(serviceParameter.findParametersByBotNameAndPriority(anyString(),anyString()).size(),3);

        verify(parameterRepository,times(1)).findParametersByBotNameAndPriority(anyString(),anyString());
    }

    @Test
    public void findParameterByScopeParameterName() throws Exception {
        Parameter parameter=new Parameter();
        List<Parameter> parameters=Lists.newArrayList(parameter,parameter,parameter,parameter);
        when(parameterRepository.findParameterByScopeParameterName(anyString())).thenReturn(parameters);

        assertEquals(serviceParameter.findParameterByScopeParameterName(anyString()).size(),4);

        verify(parameterRepository,times(1)).findParameterByScopeParameterName(anyString());
    }

    @Test
    public void findAllParametersById() throws Exception {
        Parameter parameter=new Parameter();
        Iterable<Parameter> parameters=Lists.newArrayList(parameter);

        when(parameterRepository.findByUsuarioId(anyLong())).thenReturn(parameters);
        when(parameterRepository.findAll()).thenReturn(parameters);

        assertNotNull(serviceParameter.findAllParametersById(anyLong()).iterator().next());
        verify(parameterRepository,times(1)).findByUsuarioId(anyLong());

        assertNotNull(serviceParameter.findAllParametersById(null).iterator().next());
        verify(parameterRepository,times(1)).findAll();
    }

    @Test
    public void findAll() throws Exception {
        Parameter parameter=new Parameter();
        Iterable<Parameter> parameters=Lists.newArrayList(parameter);
        when(parameterRepository.findAll()).thenReturn(parameters);

        assertNotNull(serviceParameter.findAll().iterator().next());

        verify(parameterRepository,times(1)).findAll();
    }

}
