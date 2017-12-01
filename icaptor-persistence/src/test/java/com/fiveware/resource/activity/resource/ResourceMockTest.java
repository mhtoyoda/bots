package com.fiveware.resource.activity.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveware.model.activity.RecentActivity;
import com.fiveware.model.user.IcaptorUser;
import com.fiveware.resource.activity.ResourceRecentActivity;
import com.fiveware.service.activity.ServiceActivityImpl;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnit4.class)
public class ResourceMockTest {

    @Mock
    ServiceActivityImpl serviceActivity;

    @InjectMocks
    private ResourceRecentActivity resourceRecentActivity;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(resourceRecentActivity).build();
    }

    @Test
    public void save() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RecentActivity recentActivity=new RecentActivity();
        String json= objectMapper.writeValueAsString(recentActivity);

        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/recent-activities/new-activity")
                        .accept(MediaTypes.HAL_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect((status().isCreated()));
    }



    @Test
    public void save1() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RecentActivity recentActivity=new RecentActivity();
        List<RecentActivity> activities = Lists.newArrayList(recentActivity,recentActivity,recentActivity);

        String json= objectMapper.writeValueAsString(activities);

        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/recent-activities/new-activities")
                        .accept(MediaTypes.HAL_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect((status().isCreated()));
    }

    @Test
    public void updateVisualized() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> activityIds = Arrays.asList(5L,3L,7L,4L,3L);
        String json= objectMapper.writeValueAsString(activityIds);

        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/recent-activities/mark-seen")
                        .accept(MediaTypes.HAL_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect((status().isOk()));
    }

    @Test
    public void getUnseenByUserId() throws Exception {


        RecentActivity recentActivity=new RecentActivity();
        recentActivity.setId(30L);

        RecentActivity recentActivity20=new RecentActivity();
        recentActivity20.setId(20L);

        List<RecentActivity> recentActivities=Lists.newArrayList(recentActivity,recentActivity20);
        when(serviceActivity.findAllUnseenByUserId(10L)).thenReturn(recentActivities);


        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(
                        "/api/recent-activities/load-unseen/10"
                )
                        .accept(MediaTypes.HAL_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect((status().isOk()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id",
                        Matchers.hasItems(
                                Matchers.is(20),
                                Matchers.is(30))));

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


    private String generateJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}