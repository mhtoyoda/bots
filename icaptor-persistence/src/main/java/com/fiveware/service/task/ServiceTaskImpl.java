package com.fiveware.service.task;

import com.fiveware.model.Task;
import com.fiveware.repository.TaskRepository;
import com.fiveware.repository.task.filter.TaskFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceTaskImpl {

    @Autowired
    private final TaskRepository taskRepository;

    public ServiceTaskImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task update(Task task,Long id) {
        Task one = taskRepository.findOne(id);
        one.setStatusProcess(task.getStatusProcess());
        taskRepository.save(one);
        return one;
    }

    public Task findOne(Long id) {
        return taskRepository.findOne(id);
    }

    public List<Task> filter(TaskFilter taskFilter) {
        return taskRepository.filter(taskFilter);
    }

    public List<Task> findTaskbyBot(String nameBot) {
        return taskRepository.findTaskbyBot(nameBot);
    }

    public List<Task> findTaskbyStatusProcess(String status) {
        return taskRepository.findTaskbyStatusProcess(status);
    }

    public List<Task> findPeloUsuario(Long userId) {
        List<Task> tasks= taskRepository.findPeloUsuario(userId);
        return tasks;
    }

    public List<Task> findRecentTaskByStatus(String status) {
        LocalDateTime start = LocalDateTime.now().withMinute(0).withSecond(0);
        LocalDateTime end = LocalDateTime.now().plusMinutes(90).withSecond(59);
        return taskRepository.findTaskByStatusProcessAndStartAt(status, start, end);
    }
}
