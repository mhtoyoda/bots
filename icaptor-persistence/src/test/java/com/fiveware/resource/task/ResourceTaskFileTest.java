package com.fiveware.resource.task;

import com.fiveware.model.Task;
import com.fiveware.model.TaskFile;
import com.fiveware.repository.TaskFileRepository;
import com.fiveware.repository.TaskRepository;
import com.fiveware.service.task.ServiceTaskFileImpl;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ResourceTaskFileTest {


    @Mock
    private TaskFileRepository taskFileRepository;

    @Mock
    private TaskRepository taskRepository;

    ServiceTaskFileImpl serviceTaskFileImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.serviceTaskFileImpl = new ServiceTaskFileImpl(taskFileRepository,taskRepository);
    }

    @Test
    public void save() throws Exception {
        Task task=new Task();
        TaskFile taskfile=new TaskFile();
        taskfile.setTask(task);
        when(taskRepository.findOne(anyLong())).thenReturn(task);
        when(taskFileRepository.save(taskfile)).thenReturn(taskfile);

        Assert.assertNotNull(serviceTaskFileImpl.save(taskfile));
        assertEquals(serviceTaskFileImpl.save(taskfile).getTask(),task);

        verify(taskRepository,times(2)).findOne(anyLong());
        verify(taskFileRepository,times(2)).save(taskfile);

    }

    @Test
    public void findAllByTask() throws Exception {
        TaskFile taskFile=new TaskFile();
        List<TaskFile> taskes= Lists.newArrayList(taskFile,taskFile,taskFile);
        when(taskFileRepository.findFilebyTaskId(anyLong())).thenReturn(taskes);

        assertEquals(serviceTaskFileImpl.findFilebyTaskId(anyLong()).size(),3);

        verify(taskFileRepository,times(1)).findFilebyTaskId(anyLong());
    }

    @Test
    public void delete() throws Exception {
        TaskFile taskFile=new TaskFile();
        when(taskFileRepository.findOne(anyLong())).thenReturn(taskFile);

        serviceTaskFileImpl.delete(taskFile);

        verify(taskFileRepository,times(1)).findOne(anyLong());
    }

}