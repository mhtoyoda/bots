package com.fiveware.resource.activity.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.IcaptorPersistenceApplication;
import com.fiveware.model.Bot;
import com.fiveware.model.Task;
import com.fiveware.model.activity.RecentActivity;
import com.fiveware.model.user.IcaptorUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static com.fiveware.resource.TestUtil.getTestUtil;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IcaptorPersistenceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResourceTest {

    public static final String API_RECENT_ACTIVITIES_NEW_ACTIVITY = "/api/recent-activities/new-activity";

    private static final String API_MARK_SEEN = "/api/recent-activities/mark-seen";
    private static final String API_LOAD_UNSEEN = "/api/recent-activities/load-unseen/";
    private static final String API_RECENT_ACTIVITIES_USER = "/api/recent-activities/user/";
    private static final String API_COUNT_UNSEEN = "/api/recent-activities/count-unseen/";
    private static final String API_RECENT_ACTIVITIES_TASK = "/api/recent-activities/task/";


    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private IcaptorUser user;
    private Bot bot;
    private Task task;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        user = new IcaptorUser();
        bot = new Bot();
        task=new Task();
    }

    @Test
    public void invalidRequestSave() throws Exception {
        String msgErrorUser="org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance beforeQuery " +
                "flushing : com.fiveware.model.activity.RecentActivity.user -> com.fiveware.model.user.IcaptorUser";

        String msgErrorBot="org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance beforeQuery " +
                "flushing : com.fiveware.model.activity.RecentActivity.bot -> com.fiveware.model.Bot";

        String msgErrorTask="org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance beforeQuery " +
                "flushing : com.fiveware.model.activity.RecentActivity.task -> com.fiveware.model.Task";

        RecentActivity recentActivity = new RecentActivity();
        recentActivity.setUser(user);
        String json = objectMapper.writeValueAsString(recentActivity);

        ResultActions perform = getTestUtil(mockMvc,objectMapper).postResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        perform.andExpect((status().isBadRequest()))
                 .andExpect(jsonPath("status").value("BAD_REQUEST"))
                 .andExpect(jsonPath("error"). value(msgErrorUser));


        RecentActivity recentActivityBot = new RecentActivity();
        recentActivityBot.setBot(bot);
        json = objectMapper.writeValueAsString(recentActivityBot);

        perform = getTestUtil(mockMvc,objectMapper).postResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        perform.andExpect((status().isBadRequest()))
                .andExpect(jsonPath("status").value("BAD_REQUEST"))
                .andExpect(jsonPath("error"). value(msgErrorBot));


        RecentActivity recentActivityTask = new RecentActivity();
        recentActivityTask.setTask(task);
        json = objectMapper.writeValueAsString(recentActivityTask);

        perform = getTestUtil(mockMvc,objectMapper).postResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        perform.andExpect((status().isBadRequest()))
                .andExpect(jsonPath("status").value("BAD_REQUEST"))
                .andExpect(jsonPath("error"). value(msgErrorTask));
    }

    @Test
    public void validRequestSave() throws Exception {
        RecentActivity recentActivity = new RecentActivity();
        IcaptorUser icaptorUser = getTestUtil(mockMvc,objectMapper).saveUser();

        recentActivity.setUser(icaptorUser);
        recentActivity.setMessage("task processed!");
        String json = objectMapper.writeValueAsString(recentActivity);

        ResultActions perform = getTestUtil(mockMvc,objectMapper).postResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        perform.andExpect((status().isCreated()))
                .andExpect(jsonPath("id").value(new Long(2)))
                .andExpect(jsonPath("user.active").value(true))
                .andExpect(jsonPath("user.email").value("admin@icaptor.com"))
                .andExpect(jsonPath("user.name").value("Administrator"))
                .andExpect(jsonPath("user.id").value(2))
                .andExpect(jsonPath("message").value("task processed!"));

    }

    @Test
    public void updateVisualized() throws Exception {

        RecentActivity recentActivity = new RecentActivity();
        IcaptorUser icaptorUser = getTestUtil(mockMvc,objectMapper).saveUser();

        recentActivity.setUser(icaptorUser);
        recentActivity.setMessage("task processed!");
        String json = objectMapper.writeValueAsString(recentActivity);

        getTestUtil(mockMvc,objectMapper).postResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);


        List<Integer> integers = Arrays.asList(1, 3, 4);

        json = objectMapper.writeValueAsString(integers);

        getTestUtil(mockMvc,objectMapper).putResultActions(json, API_MARK_SEEN);

        ResultActions perform2 = getTestUtil(mockMvc,objectMapper).getResultActions(API_RECENT_ACTIVITIES_USER.concat("1"));
        perform2.andExpect((status().isOk()))
                .andDo(print())
                .andExpect(jsonPath("$[0].visualized").value(true));
    }

    @Test
    public void getUnseenByUserId() throws Exception {
        RecentActivity recentActivity = new RecentActivity();
        IcaptorUser icaptorUser = getTestUtil(mockMvc,objectMapper).saveUser();

        recentActivity.setUser(icaptorUser);
        recentActivity.setMessage("task processed!");
        String json = objectMapper.writeValueAsString(recentActivity);

        getTestUtil(mockMvc,objectMapper).postResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        ResultActions perform = getTestUtil(mockMvc,objectMapper).getResultActions(API_LOAD_UNSEEN.concat("2"));
        perform.andExpect((status().isOk()))
                .andExpect(jsonPath("$[0].user.id").value(new Long(2)));
    }

    @Test
    public void countUnseenByUserId() throws Exception {
        ResultActions perform = getTestUtil(mockMvc,objectMapper).getResultActions(API_COUNT_UNSEEN.concat("2"));
        perform.andExpect((status().isOk()))
                .andExpect(jsonPath("$").value(new Long(1)));
    }

    @Test
    public void getByUserId() throws Exception {
        ResultActions perform = getTestUtil(mockMvc,objectMapper).getResultActions(API_RECENT_ACTIVITIES_USER.concat("1"));
        perform.andExpect((status().isOk()))
                .andDo(print())
                .andExpect(jsonPath("$[0].user.id").value(new Long(1)));

    }

    @Test
    public void getByTaskId() throws Exception {
        RecentActivity recentActivity = new RecentActivity();
        Task task = getTestUtil(mockMvc,objectMapper).saveTask();

        recentActivity.setTask(task);
        recentActivity.setMessage("task processed!");
        String json = objectMapper.writeValueAsString(recentActivity);

        getTestUtil(mockMvc,objectMapper).postResultActions(json,API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        ResultActions perform = getTestUtil(mockMvc,objectMapper).getResultActions(API_RECENT_ACTIVITIES_TASK.concat("1"));
        perform.andExpect((status().isOk()))
                .andDo(print())
                .andExpect(jsonPath("$[0].task.id").value(new Long(1)));

    }
}