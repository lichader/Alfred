package com.lichader.alfred.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lichader.alfred.controller.metro.MetroServiceCheckController;
import com.lichader.alfred.logic.TrainlineDisruptionRetrievalLogic;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.servant.MetroAlfred;
import com.lichader.alfred.util.SerializationHelperFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetroServiceCheckController.class)
public class MetroServiceCheckControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainlineDisruptionRetrievalLogic logic;

    @MockBean
    private MetroAlfred metroAlfred;

    @Test
    public void getDisruptions_ExpectOneReturn() throws Exception{
        Disruption disruption = new Disruption();
        disruption.Description = "Test Disruption";

        List<Disruption> disruptions = new ArrayList<>();
        disruptions.add(disruption);

        when(logic.findDisruptions()).thenReturn(disruptions);
        mockMvc.perform(get("api/metro/disruptions")).andExpect(status().isOk());

//        String content = result.getResponse().getContentAsString();
//        ObjectMapper mapper = SerializationHelperFactory.getHelper();
//        List serialisedResult = mapper.readValue(content, List.class);
//
//        assertEquals(1, serialisedResult.size());
    }

}
