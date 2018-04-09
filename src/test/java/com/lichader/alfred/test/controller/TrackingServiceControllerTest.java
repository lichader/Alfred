package com.lichader.alfred.test.controller;

import com.lichader.alfred.controller.tracking.TrackingServiceController;
import com.lichader.alfred.db.dao.TrackingParcelRepository;
import com.lichader.alfred.db.model.tracking.TrackingParcel;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackingServiceController.class)
public class TrackingServiceControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackingParcelRepository trackingParcelRepository;

    @Test
    public void noRecordFound_ExpectNotFoundResponse() throws Exception {
        when(trackingParcelRepository.findByTrackingNoIgnoreCase(Mockito.anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/trackings/test123")).andExpect(status().isNotFound());
    }

    @Test
    public void oneRecordFound_ExpectSuccessResponse() throws Exception {
        TrackingParcel trackingParcel = new TrackingParcel();
        final String trackingNo = "N12312";
        trackingParcel.setTrackingNo(trackingNo);
        trackingParcel.setDelivered(true);

        List<TrackingParcel> parcels = new ArrayList<>();
        parcels.add(trackingParcel);

        when(trackingParcelRepository.findByTrackingNoIgnoreCase(trackingNo)).thenReturn(parcels);
        mockMvc.perform(get("/api/trackings/" + trackingNo)).andExpect(status().isOk());
    }


    @Test
    public void createNewTrackingParcel_ExpectSuccessResponse() throws Exception {
        final String trackingNo = "CN12321";
        String jsonRequest = "{ \"trackingNo\": \"" + trackingNo +  "\" }";

        mockMvc.perform(post("/api/trackings").content(jsonRequest).contentType("application/json"))
            .andExpect(status().isCreated());

        ArgumentCaptor<TrackingParcel> argument = ArgumentCaptor.forClass(TrackingParcel.class);
        then(trackingParcelRepository).should(times(1)).save(argument.capture());

        assertEquals("CN12321", argument.getValue().getTrackingNo());
        assertEquals("中国", argument.getValue().getDestination());
    }
}
