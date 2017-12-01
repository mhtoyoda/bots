package com.fiveware.resource.task;

import com.fiveware.model.ItemTask;
import com.fiveware.model.StatusProcessItemTask;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.repository.ItemTaskRepository;
import com.fiveware.service.task.ServiceItemTaskImpl;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class ResourceItemTaskTest {

    @Mock
    private ItemTaskRepository itemTaskRepository;

    ServiceItemTaskImpl serviceItemTask;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.serviceItemTask = new ServiceItemTaskImpl(itemTaskRepository);
    }

    @Test
    public void findOne() throws Exception {
        ItemTask itemTask=new ItemTask();
        when(itemTaskRepository.findOne(anyLong())).thenReturn(itemTask);
        assertNotNull(serviceItemTask.findOne(anyLong()));
        verify(itemTaskRepository,times(1)).findOne(anyLong());
    }

    @Test
    public void findAll() throws Exception {
        ItemTask itemTask=new ItemTask();
        Iterable<ItemTask> itemTaskes= Lists.newArrayList(itemTask);
        when(itemTaskRepository.findAll()).thenReturn(itemTaskes);

        assertNotNull(serviceItemTask.findAll().iterator().next());

        verify(itemTaskRepository).findAll();
    }

    @Test
    public void findAllByTask() throws Exception {
        ItemTask itemTask=new ItemTask();
        List<ItemTask> itemTaskes=Lists.newArrayList(itemTask,itemTask,itemTask,itemTask);
        when(itemTaskRepository.findByTaskId(anyLong())).thenReturn(itemTaskes);

        assertEquals(serviceItemTask.findAllByTask(anyLong()).size(),4);

        verify(itemTaskRepository).findByTaskId(anyLong());
    }

    @Test
    public void save() throws Exception {
        ItemTask itemTask=new ItemTask();
        when(itemTaskRepository.save(itemTask)).thenReturn(itemTask);

        assertNotNull(serviceItemTask.save(itemTask));
        verify(itemTaskRepository).save(itemTask);
    }

    @Test
    public void update() throws Exception {
        StatusProcessItemTask statuProcessItemTask = StatusProcessItemTaskEnum.PROCESSING.getStatuProcess();

        ItemTask itemTask=new ItemTask();
        itemTask.setStatusProcess(statuProcessItemTask);
        when(itemTaskRepository.findOne(anyLong())).thenReturn(itemTask);

        assertNotNull(serviceItemTask.update(itemTask,anyLong()));
        assertEquals(serviceItemTask
                .update(itemTask,anyLong()).getStatusProcess(),statuProcessItemTask);

        verify(itemTaskRepository,times(2)).findOne(anyLong());
        verify(itemTaskRepository,times(2)).save(itemTask);
    }

    @Test
    public void findByStatus() throws Exception {
        ItemTask itemTask=new ItemTask();
        List<ItemTask> itemTaskes=Lists.newArrayList(itemTask,itemTask,itemTask);
        when(itemTaskRepository.findItemTaskbyStatusProcess(anyString())).thenReturn(itemTaskes);

        assertEquals(serviceItemTask.findByStatus(anyString()).size(),3);

        verify(itemTaskRepository).findItemTaskbyStatusProcess(anyString());
    }

    @Test
    public void findItemTaskbyListStatusProcess() throws Exception {
        ItemTask itemTask=new ItemTask();
        List<ItemTask> itemTaskes=Lists.newArrayList(itemTask,itemTask);
        when(itemTaskRepository.findItemTaskbyListStatusProcess(anyLong(), anyList()))
                                    .thenReturn(itemTaskes);

        assertEquals(serviceItemTask.findItemTaskbyListStatusProcess(anyLong(),anyList()).size(),2);

        verify(itemTaskRepository).findItemTaskbyListStatusProcess(anyLong(), anyList());
    }

    @Test
    public void countItemTask() throws Exception {
        when(itemTaskRepository.countItemTask(anyLong())).thenReturn(3L);

        assertEquals(serviceItemTask.countItemTask(anyLong()).longValue(),3L);

        verify(itemTaskRepository,times(1)).countItemTask(anyLong());
    }

}