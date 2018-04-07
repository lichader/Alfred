package com.lichader.alfred.test.controller;

import com.lichader.alfred.controller.tracking.TrackingServiceController;
import com.lichader.alfred.db.dao.TrackingParcelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackingServiceController.class)
@ActiveProfiles("test")
public class TrackingServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackingParcelRepository trackingParcelRepository;

    @Test
    public void testNoRecordFound_ExpectNotFoundResponse() throws Exception {
        when(trackingParcelRepository.findByTrackingNo(Mockito.anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/trackings/test123")).andExpect(status().is4xxClientError());
    }
}
