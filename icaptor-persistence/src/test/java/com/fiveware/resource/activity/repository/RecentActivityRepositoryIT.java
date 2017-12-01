package com.fiveware.resource.activity.repository;

import com.fiveware.IcaptorPersistenceApplication;
import com.fiveware.model.Bot;
import com.fiveware.model.StatusProcessTaskEnum;
import com.fiveware.model.Task;
import com.fiveware.model.activity.RecentActivity;
import com.fiveware.model.user.IcaptorUser;
import com.fiveware.repository.BotRepository;
import com.fiveware.repository.TaskRepository;
import com.fiveware.repository.activity.RecentActivityRepository;
import com.fiveware.repository.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IcaptorPersistenceApplication.class)
@ActiveProfiles("test")
public class RecentActivityRepositoryIT {
    @Autowired
    private RecentActivityRepository recentActivityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private TaskRepository taskRepository;

    private RecentActivity recentActivity;
    private IcaptorUser user;
    private Bot bot;
    private Task task;

    @Before
    public void setUp() throws Exception {
        user = new IcaptorUser();
        bot = new Bot();
        recentActivity = new RecentActivity();
        task = new Task();
    }

    @Test
    public void saveWithUser() throws Exception {
        recentActivity.setUser(saveUser());
        recentActivityRepository.save(recentActivity);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void invalidSaveWithBot() throws Exception {
        recentActivity.setBot(bot);
        recentActivityRepository.save(recentActivity);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void invalidSaveWithTask() throws Exception {
        recentActivity.setTask(task);
        recentActivityRepository.save(recentActivity);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void exceptionFindAllByUserId() throws Exception {
        user.setActive(true);
        user.setEmail("admin@icaptor.com");
        user.setName("Administrator");
        recentActivity.setUser(user);
        recentActivityRepository.save(recentActivity);

        List<RecentActivity> allByUserId = recentActivityRepository.findAllByUserId(recentActivity.getUser().getId());
        assertEquals(allByUserId.size(), 1);
        assertEquals(allByUserId.get(0).getId().longValue(), 1L);
        assertEquals(allByUserId.get(0).getUser().getName(), user.getName());
    }

    @Test
    public void saveWithBot() throws Exception {
        recentActivity.setBot(saveBot());
        recentActivityRepository.save(recentActivity);
    }

    @Test
    public void saveWithTask() throws Exception {
        recentActivity.setTask(saveTask());
        recentActivityRepository.save(recentActivity);
    }

    @Test
    public void findAllByUserId() throws Exception {
        saveWithUser();
        List<RecentActivity> allByUserId = recentActivityRepository.findAllByUserId(recentActivity.getUser().getId());
        assertEquals(allByUserId.size(), 1);
        assertEquals(allByUserId.get(0).getId().longValue(), 1L);
        assertEquals(allByUserId.get(0).getUser().getName(), user.getName());
    }

    @Test
    public void findAllUnseenByUserId() throws Exception {
    }

    @Test
    public void countUnseenByUser() throws Exception {
    }

    @Test
    public void setVisualized() throws Exception {
    }

    @Test
    public void findByTaskId() throws Exception {
    }


    private IcaptorUser saveUser() {
        user.setActive(true);
        user.setEmail("admin@icaptor.com");
        user.setName("Administrator");
        return userRepository.save(user);
    }

    private Bot saveBot() {
        bot.setNameBot("botTeste");
        return botRepository.save(bot);
    }

    private Task saveTask() {
        task.setLoadTime(LocalDateTime.now());
        return taskRepository.save(task);
    }

}