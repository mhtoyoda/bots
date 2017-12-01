package com.fiveware.resource.user;

import com.fiveware.model.user.IcaptorUser;
import com.fiveware.repository.user.UserRepository;
import com.fiveware.service.user.ServiceUserImpl;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class UserResourceTest {


    @Mock
    ApplicationEventPublisher publisher;

    @Mock
    UserRepository userRepository;


    ServiceUserImpl serviceUser;

    IcaptorUser usuario;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.serviceUser = new ServiceUserImpl(publisher,userRepository);
        usuario=new IcaptorUser();
    }

    @Test
    public void create() throws Exception {
        HttpServletResponse spy = spy(HttpServletResponse.class);

        when(userRepository.save(usuario)).thenReturn(usuario);

        Assert.assertNotNull(serviceUser.create(usuario,spy));

        verify(userRepository,times(1)).save(usuario);
    }

    @Test
    public void getUsuario() throws Exception {
        when(userRepository.findByEmailAndActive(usuario.getEmail(),true)).thenReturn(Optional.of(usuario));

        assertNotNull(serviceUser.getUsuario(usuario));

        verify(userRepository,times(1)).findByEmailAndActive(usuario.getEmail(),true);
    }

    @Test
    public void get() throws Exception {
        when(userRepository.findOne(anyLong())).thenReturn(usuario);
        assertNotNull(serviceUser.get(anyLong()));

        verify(userRepository,times(1)).findOne(anyLong());
    }

    @Test
    public void list() throws Exception {
        Iterable<IcaptorUser> users= Lists.newArrayList(usuario);
        when(userRepository.findAll()).thenReturn(users);

        assertNotNull(serviceUser.list().iterator().next());

        verify(userRepository,times(1)).findAll();
    }

}