package com.lichader.alfred.test.controller;

import com.lichader.alfred.controller.tracking.TrackingServiceController;
import com.lichader.alfred.db.dao.TrackingParcelRepository;
import com.lichader.alfred.db.dao.TrackingStatusRepository;
import com.lichader.alfred.db.model.tracking.TrackingHistory;
import com.lichader.alfred.db.model.tracking.TrackingParcel;
import com.lichader.alfred.db.model.tracking.TrackingStatus;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackingServiceController.class)
public class TrackingServiceControllerTest extends AbstractControllerTest{

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackingParcelRepository trackingParcelRepository;

    @MockBean
    private TrackingStatusRepository trackingStatusRepository;

    @Test
    public void noRecordFound_ExpectNotFoundResponse() throws Exception {
        when(trackingParcelRepository.findOneByTrackingNoIgnoreCase(Mockito.anyString())).thenReturn(null);
        mockMvc.perform(get("/api/trackings/test123")).andExpect(status().isNotFound());
    }

    @Test
    public void oneRecordFound_ExpectSuccessResponse() throws Exception {
        final String trackingNo = "N12312";
        final String status = "In progress";
        final String location = "Melbourne";
        final String historyStatus = "Out for delivery";

        TrackingParcel trackingParcel = new TrackingParcel();
        trackingParcel.setTrackingNo(trackingNo);
        trackingParcel.setStatus(new TrackingStatus(1, status));

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setStatus(historyStatus);
        trackingHistory.setLocation(location);
        LocalDateTime now = LocalDateTime.now();
        trackingHistory.setTime(now);
        trackingParcel.getHistories().add(trackingHistory);

        when(trackingParcelRepository.findOneByTrackingNoIgnoreCase(trackingNo)).thenReturn(trackingParcel);
        mockMvc.perform(get("/api/trackings/" + trackingNo))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.trackingNo").value(trackingNo))
            .andExpect(jsonPath("$.status").value(status))
            .andExpect(jsonPath("$.histories").isArray())
            .andExpect(jsonPath("$.histories[0].location").value(location))
            .andExpect(jsonPath("$.histories[0].status").value(historyStatus))
            .andExpect(jsonPath("$.histories[0].time").value(now.format(formatter)));
    }


    @Test
    public void createNewTrackingParcel_ExpectSuccessResponse() throws Exception {
        final String trackingNo = "CN12321";
        String jsonRequest = "{ \"trackingNo\": \"" + trackingNo +  "\" }";

        when(trackingParcelRepository.findOneByTrackingNoIgnoreCase(trackingNo)).thenReturn(null);

        TrackingParcel trackingParcel = new TrackingParcel();
        trackingParcel.setId(1l);
        trackingParcel.setTrackingNo(trackingNo);
        trackingParcel.setStatus(new TrackingStatus(1, "In progress"));

        when(trackingParcelRepository.save(any(TrackingParcel.class))).thenReturn(trackingParcel);

        mockMvc.perform(post("/api/trackings").content(jsonRequest).contentType("application/json"))
            .andExpect(status().isCreated());

        ArgumentCaptor<TrackingParcel> argument = ArgumentCaptor.forClass(TrackingParcel.class);
        then(trackingParcelRepository).should(times(1)).save(argument.capture());

        assertEquals("CN12321", argument.getValue().getTrackingNo());
    }

    @Test
    public void createDuplicateTrackingParcel_ExpectConflictResponse() throws Exception{
        final String trackingNo = "CN12321";
        String jsonRequest = "{ \"trackingNo\": \"" + trackingNo +  "\" }";

        TrackingParcel trackingParcel = new TrackingParcel();
        trackingParcel.setTrackingNo(trackingNo);
        trackingParcel.setStatus(new TrackingStatus(1, "In progress"));
        when(trackingParcelRepository.findOneByTrackingNoIgnoreCase(trackingNo)).thenReturn(trackingParcel);

        mockMvc.perform(post("/api/trackings").content(jsonRequest).contentType("application/json"))
            .andExpect(status().isConflict());
    }
}
