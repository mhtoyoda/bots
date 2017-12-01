package com.fiveware.resource.task;

import com.fiveware.model.StatusProcessTask;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.repository.TaskRepository;
import com.fiveware.repository.task.filter.TaskFilter;
import com.fiveware.service.task.ServiceTaskImpl;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ResourceTaskTest {

    @Mock
    private TaskRepository taskRepository;

    ServiceTaskImpl serviceTask;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.serviceTask = new ServiceTaskImpl(taskRepository);
    }

    @Test
    public void save() throws Exception {
        Task task=new Task();
        when(taskRepository.save(task)).thenReturn(task);

        Assert.assertNotNull(serviceTask.save(task));

        verify(taskRepository,times(1)).save(task);
    }

    @Test
    public void update() throws Exception {
        StatusProcessTask statuProcess = StatusProcessTaskEnum.PROCESSED.getStatuProcess();
        Task task=new Task();
        task.setStatusProcess(statuProcess);
        when(taskRepository.findOne(anyLong())).thenReturn(task);

        assertNotNull(serviceTask.update(task,anyLong()));
        assertEquals(serviceTask.update(task,anyLong()).getStatusProcess().getName()
                                            ,StatusProcessTaskEnum.PROCESSED.getStatuProcess().getName());

        verify(taskRepository,times(2)).findOne(anyLong());
        verify(taskRepository,times(2)).save(task);

    }

    @Test
    public void findOne() throws Exception {
        Task task=new Task();
        when(taskRepository.findOne(anyLong())).thenReturn(task);

        assertNotNull(serviceTask.findOne(anyLong()));

        verify(taskRepository,times(1)).findOne(anyLong());
    }

    @Test
    public void filter() throws Exception {
        Task task=new Task();
        List<Task> taskes= Lists.newArrayList(task,task,task);
        TaskFilter taskFilter=new TaskFilter();
        when(taskRepository.filter(taskFilter)).thenReturn(taskes);

        assertEquals(serviceTask.filter(taskFilter).size(),3);
        verify(taskRepository,times(1)).filter(taskFilter);
    }

    @Test
    public void findByBot() throws Exception {
        Task task=new Task();
        List<Task> taskes=Lists.newArrayList(task,task,task);
        when(taskRepository.findTaskbyBot(anyString())).thenReturn(taskes);

        assertEquals(serviceTask.findTaskbyBot(anyString()).size(),3);
        verify(taskRepository,times(1)).findTaskbyBot(anyString());
    }

    @Test
    public void findByStatus() throws Exception {
        Task task=new Task();
        List<Task> taskes=Lists.newArrayList(task,task,task);
        when(taskRepository.findTaskbyStatusProcess(anyString())).thenReturn(taskes);

        assertEquals(serviceTask.findTaskbyStatusProcess(anyString()).size(),3);

        verify(taskRepository,times(1)).findTaskbyStatusProcess(anyString());
    }

    @Test
    public void findPeloUsuario() throws Exception {
        Task task=new Task();
        List<Task> taskes=Lists.newArrayList(task,task,task,task);
        when(taskRepository.findPeloUsuario(anyLong())).thenReturn(taskes);

        assertEquals(serviceTask.findPeloUsuario(anyLong()).size(),4);
        verify(taskRepository,times(1)).findPeloUsuario(anyLong());
    }

    public void findRecentTaskByStatus() throws Exception {
        LocalDateTime start=LocalDateTime.now().withMinute(59).withSecond(59);
        LocalDateTime end=LocalDateTime.now().withMinute(59).withSecond(59);

        Task task=new Task();
        List<Task> taskes=Lists.newArrayList(task);
//        when(taskRepository.findTaskByStatusProcessAndStartAt(anyString(),start,end))
//                           .thenReturn(taskes);

        assertEquals(serviceTask.findRecentTaskByStatus(anyString()).size(),0);

//        verify(taskRepository,times(1)).findTaskByStatusProcessAndStartAt(anyString(),start,end);

    }

}