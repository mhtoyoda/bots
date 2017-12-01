package com.fiveware.service.task;

import com.fiveware.model.ItemTask;
import com.fiveware.repository.ItemTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceItemTaskImpl {

    @Autowired
    private final ItemTaskRepository itemTaskRepository;

    public ServiceItemTaskImpl(ItemTaskRepository itemTaskRepository) {
        this.itemTaskRepository = itemTaskRepository;
    }

    public ItemTask findOne(Long id) {
        return itemTaskRepository.findOne(id);
    }

    public Iterable<ItemTask> findAll() {
        return itemTaskRepository.findAll();
    }

    public List<ItemTask> findAllByTask(Long id) {
        return itemTaskRepository.findByTaskId(id);
    }

    public ItemTask save(ItemTask itemTask) {
        itemTask = itemTaskRepository.save(itemTask);
        return itemTask;
    }

    public ItemTask update(ItemTask paramItemTask,Long id) {
        ItemTask itemTask = itemTaskRepository.findOne(id);
        itemTask.setStatusProcess(paramItemTask.getStatusProcess());
        itemTaskRepository.save(itemTask);
        return itemTask;
    }

    public List<ItemTask> findByStatus(String status) {
        return itemTaskRepository.findItemTaskbyStatusProcess(status);
    }

    public List<ItemTask> findItemTaskbyListStatusProcess(Long taskId,List<String> status) {
        return itemTaskRepository.findItemTaskbyListStatusProcess(taskId, status);
    }

    public Long countItemTask(Long taskId) {
        return itemTaskRepository.countItemTask(taskId);
    }
}
