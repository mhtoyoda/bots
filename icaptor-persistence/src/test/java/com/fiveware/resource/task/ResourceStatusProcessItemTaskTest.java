package com.fiveware.resource.task;

import com.fiveware.model.StatusProcessItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.repository.StatuProcessItemTaskRepository;
import com.fiveware.repository.StatuProcessRepository;
import com.fiveware.service.task.ServiceStatusProcessImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class ResourceStatusProcessItemTaskTest {

    @Mock
    private StatuProcessRepository statuProcessRepository;

    @Mock
    private StatuProcessItemTaskRepository statuProcessItemTaskRepository;

    ServiceStatusProcessImpl serviceStatusProcess;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.serviceStatusProcess = new ServiceStatusProcessImpl(statuProcessRepository,
                                                            statuProcessItemTaskRepository);
    }

    @Test
    public void findOne() throws Exception {
        StatusProcessItemTask status= StatusProcessItemTaskEnum.PROCESSING.getStatuProcess();
        when(statuProcessItemTaskRepository.findOne(anyLong())).thenReturn(status);
        assertEquals(serviceStatusProcess.getStatusProcessItemTask(anyLong()),status);
        verify(statuProcessItemTaskRepository,times(1)).findOne(anyLong());
    }

}