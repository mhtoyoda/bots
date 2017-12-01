package com.fiveware.resource.task;

import com.fiveware.model.StatusProcessTask;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.repository.StatuProcessItemTaskRepository;
import com.fiveware.repository.StatuProcessRepository;
import com.fiveware.service.task.ServiceStatusProcessImpl;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ResourceStatusProcessTaskTest {

    @Mock
    private  StatuProcessRepository statuProcessRepository;

    @Mock
    private  StatuProcessItemTaskRepository repositoryStatusItem;

    ServiceStatusProcessImpl serviceStatusProcess;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.serviceStatusProcess = new ServiceStatusProcessImpl(statuProcessRepository,repositoryStatusItem);
    }

    @Test
    public void findOne() throws Exception {
        StatusProcessTask status= StatusProcessTaskEnum.PROCESSED.getStatuProcess();

        when(statuProcessRepository.findOne(anyLong())).thenReturn(status);
        assertEquals(serviceStatusProcess.getStatusProcessTask(anyLong()),status);
        verify(statuProcessRepository,times(1)).findOne(anyLong());
    }

    @Test
    public void list() throws Exception {
        Iterable<StatusProcessTask> statuses= Lists.newArrayList(
                        StatusProcessTaskEnum.PROCESSING.getStatuProcess(),
                        StatusProcessTaskEnum.CREATED.getStatuProcess(),
                        StatusProcessTaskEnum.ERROR.getStatuProcess()
        );
        when(statuProcessRepository.findAll()).thenReturn(statuses);

        assertEquals(serviceStatusProcess.list().iterator().next().getName(),
                StatusProcessTaskEnum.PROCESSING.getStatuProcess().getName());

        verify(statuProcessRepository,times(1)).findAll();
    }

}