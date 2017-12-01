package com.fiveware.service.task;

import com.fiveware.model.StatusProcessItemTask;
import com.fiveware.model.StatusProcessTask;
import com.fiveware.repository.StatuProcessItemTaskRepository;
import com.fiveware.repository.StatuProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceStatusProcessImpl {

    @Autowired
    private final StatuProcessRepository statuProcessRepository;

    @Autowired
    private final StatuProcessItemTaskRepository statuProcessItemTaskRepository;

    public ServiceStatusProcessImpl(StatuProcessRepository statuProcessRepository,
                                    StatuProcessItemTaskRepository statuProcessItemTaskRepository) {
        this.statuProcessRepository = statuProcessRepository;
        this.statuProcessItemTaskRepository = statuProcessItemTaskRepository;
    }


    public StatusProcessItemTask getStatusProcessItemTask(Long id) {
        return statuProcessItemTaskRepository.findOne(id);
    }


    public StatusProcessTask getStatusProcessTask(Long id) {
        return statuProcessRepository.findOne(id);
    }

    public Iterable<StatusProcessTask> list() {
        return statuProcessRepository.findAll();
    }

}
