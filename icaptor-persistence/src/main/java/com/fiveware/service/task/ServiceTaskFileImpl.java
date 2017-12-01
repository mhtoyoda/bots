package com.fiveware.service.task;

import com.fiveware.model.Task;
import com.fiveware.model.TaskFile;
import com.fiveware.repository.TaskFileRepository;
import com.fiveware.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTaskFileImpl {

    @Autowired
    private final TaskFileRepository taskFileRepository;

    @Autowired
    private final TaskRepository taskRepository;

    public ServiceTaskFileImpl(TaskFileRepository taskFileRepository, TaskRepository taskRepository) {
        this.taskFileRepository = taskFileRepository;
        this.taskRepository = taskRepository;
    }

    public TaskFile save(TaskFile taskFile) {
        Task task = taskRepository.findOne(taskFile.getTask().getId());
        taskFile.setTask(task);
        taskFile = taskFileRepository.save(taskFile);
        return taskFile;
    }

    public List<TaskFile> findFilebyTaskId( Long id) {
        return taskFileRepository.findFilebyTaskId(id);
    }

    public void delete(TaskFile taskFile) {
        taskFile = taskFileRepository.findOne(taskFile.getId());
        taskFileRepository.delete(taskFile);
    }
}
