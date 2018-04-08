package com.lichader.alfred.test.controller;

import com.lichader.alfred.controller.tracking.TrackingServiceController;
import com.lichader.alfred.db.dao.TrackingParcelRepository;
import com.lichader.alfred.db.model.tracking.TrackingParcel;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackingServiceController.class)
public class TrackingServiceControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackingParcelRepository trackingParcelRepository;

    @Test
    public void testNoRecordFound_ExpectNotFoundResponse() throws Exception {
        when(trackingParcelRepository.findByTrackingNo(Mockito.anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/trackings/test123")).andExpect(status().is4xxClientError());
    }

    @Test
    public void testOneRecordFound_ExpectSuccessResponse() throws Exception {
        TrackingParcel trackingParcel = new TrackingParcel();
        final String trackingNo = "N12312";
        trackingParcel.setTrackingNo(trackingNo);
        trackingParcel.setDelivered(true);

        List<TrackingParcel> parcels = new ArrayList<>();
        parcels.add(trackingParcel);

        when(trackingParcelRepository.findByTrackingNo(trackingNo)).thenReturn(parcels);
        mockMvc.perform(get("/api/trackings/" + trackingNo)).andExpect(status().isOk());
    }
}
