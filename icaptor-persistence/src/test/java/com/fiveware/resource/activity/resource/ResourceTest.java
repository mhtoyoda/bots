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
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = IcaptorPersistenceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ResourceTest {

    public static final String API_RECENT_ACTIVITIES_NEW_ACTIVITY = "/api/recent-activities/new-activity";
    public static final String API_USER = "/api/usuario";
    public static final String API_BOT = "/api/bot";


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

        ResultActions perform = getResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        perform.andExpect((status().isBadRequest()))
                 .andExpect(jsonPath("status").value("BAD_REQUEST"))
                 .andExpect(jsonPath("error"). value(msgErrorUser));


        RecentActivity recentActivityBot = new RecentActivity();
        recentActivityBot.setBot(bot);
        json = objectMapper.writeValueAsString(recentActivityBot);

        perform = getResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        perform.andExpect((status().isBadRequest()))
                .andExpect(jsonPath("status").value("BAD_REQUEST"))
                .andExpect(jsonPath("error"). value(msgErrorBot));


        RecentActivity recentActivityTask = new RecentActivity();
        recentActivityTask.setTask(task);
        json = objectMapper.writeValueAsString(recentActivityTask);

        perform = getResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        perform.andExpect((status().isBadRequest()))
                .andExpect(jsonPath("status").value("BAD_REQUEST"))
                .andExpect(jsonPath("error"). value(msgErrorTask));
    }

    @Test
    public void validRequestSave() throws Exception {
        RecentActivity recentActivity = new RecentActivity();
        IcaptorUser icaptorUser = saveUser();

        recentActivity.setUser(icaptorUser);
        recentActivity.setMessage("task processed!");
        String json = objectMapper.writeValueAsString(recentActivity);

        ResultActions perform = getResultActions(json, API_RECENT_ACTIVITIES_NEW_ACTIVITY);

        perform.andExpect((status().isCreated()))
                .andExpect(jsonPath("id").value(new Long(1)))
                .andExpect(jsonPath("user.active").value(true))
                .andExpect(jsonPath("user.email").value("admin@icaptor.com"))
                .andExpect(jsonPath("user.name").value("Administrator"))
                .andExpect(jsonPath("user.id").value(1))
                .andExpect(jsonPath("message").value("task processed!"));

    }

    private ResultActions getResultActions(String json, String endPoint) throws Exception {
        return mockMvc.perform(
                    MockMvcRequestBuilders.post(endPoint)
                            .accept(MediaTypes.HAL_JSON)
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void save1() throws Exception {
    }

    @Test
    public void updateVisualized() throws Exception {
    }

    @Test
    public void getUnseenByUserId() throws Exception {


    }

    @Test
    public void countUnseenByUserId() throws Exception {
    }

    @Test
    public void getByUserId() throws Exception {
    }

    @Test
    public void getByTaskId() throws Exception {
    }


    private IcaptorUser saveUser() throws Exception {
        user.setActive(true);
        user.setEmail("admin@icaptor.com");
        user.setName("Administrator");
        String json = objectMapper.writeValueAsString(user);
        ResultActions perform = getResultActions(json, API_USER);
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString,IcaptorUser.class);
    }

    private Bot saveBot() throws Exception {
        bot.setNameBot("bot-teste");
        String json = objectMapper.writeValueAsString(bot);
        ResultActions perform = getResultActions(json, API_BOT);
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString,Bot.class);
    }
}