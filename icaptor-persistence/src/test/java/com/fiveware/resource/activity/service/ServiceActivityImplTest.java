package com.fiveware.resource.activity.service;

import com.fiveware.model.activity.RecentActivity;
import com.fiveware.repository.activity.RecentActivityRepository;
import com.fiveware.service.activity.ServiceActivityImpl;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class ServiceActivityImplTest {

    @Mock
    private RecentActivityRepository recentActivityRepository;

    private ServiceActivityImpl serviceActivity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        serviceActivity = new ServiceActivityImpl(recentActivityRepository);
    }

    @Test
    public void save() {
        RecentActivity activity = new RecentActivity();
        spy(serviceActivity).save(activity);
        verify(recentActivityRepository).save(activity);
    }

    @Test
    public void setVisualized() {
        spy(serviceActivity).setVisualized(anyList());
        verify(recentActivityRepository).setVisualized(anyList());
    }

    @Test
    public void findAllUnseenByUserId() {
        RecentActivity recentActivity=new RecentActivity();
        when(serviceActivity.findAllUnseenByUserId(anyLong())).thenReturn(Lists.newArrayList(recentActivity));

        assertEquals(serviceActivity.findAllUnseenByUserId(anyLong()).size(),1);
        verify(recentActivityRepository,atLeast(1)).findAllUnseenByUserId(anyLong());
    }

    @Test
    public void countUnseenByUser() {
        Long count = new Long(1);
        when(serviceActivity.countUnseenByUser(anyLong())).thenReturn(count);
    }

    @Test
    public void getByUserId() {
        RecentActivity activity=new RecentActivity();
        when(serviceActivity.getByUserId(anyLong())).thenReturn(Lists.newArrayList(activity));

        assertEquals(serviceActivity.getByUserId(anyLong()).size(),1);
        verify(recentActivityRepository,atLeast(1)).findAllByUserId(anyLong());
    }

    @Test
    public void getByTaskId() {
        RecentActivity activity=new RecentActivity();
        when(serviceActivity.getByTaskId(anyLong())).thenReturn(Lists.newArrayList(activity));

        assertEquals(serviceActivity.getByTaskId(anyLong()).size(),1);
        verify(recentActivityRepository,atLeast(1)).findByTaskId(anyLong());
    }
}