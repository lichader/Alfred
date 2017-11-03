package com.lichader.test.alfred.unit.metro.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.metroapi.v3.model.DisruptionsResponse;
import com.lichader.alfred.util.SerializationHelperFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MetroModelDeserializationTest {

    private final ZoneId DEFAULT_TIME_ZONE = ZoneId.of("UTC");
    private String disruptionResponseFilePath = "response/hurtsbridge-disruption-response.json";

    @Test
    public void deserializeDisruptionResponse_ExpectSuccessful() throws IOException {
        String response = getFileWithUtil(disruptionResponseFilePath);

        ObjectMapper mapper = SerializationHelperFactory.getHelper();
        DisruptionsResponse typed =  mapper.readValue(response, DisruptionsResponse.class);

        assertNotNull(typed.disruptions);
        assertEquals(1, typed.disruptions.MetroTrain.size());

        Disruption disruption = typed.disruptions.MetroTrain.get(0);

        assertEquals(111153, disruption.Id);
        assertEquals("Hurstbridge line: Buses replacing trains after 8.15pm from Sunday 5 November to Wednesday, 8 November 2017",
                disruption.Title);
        assertEquals("http://ptv.vic.gov.au/live-travel-updates/article/hurstbridge-line-buses-replacing-trains-after-8-15pm-from-sunday-5-november-to-wednesday-8-november-2017",
                disruption.Url);
        assertEquals("Planned", disruption.Status);
        assertEquals("Planned Works", disruption.Type);
        assertEquals(ZonedDateTime.of(2017, 10, 27, 6, 24, 8, 0, DEFAULT_TIME_ZONE),
                disruption.PublishedOn);
        assertEquals(ZonedDateTime.of(2017, 10, 27, 6, 45, 7, 0, DEFAULT_TIME_ZONE),
                disruption.LastUpdated);
        assertEquals(ZonedDateTime.of(2017, 11, 5, 9, 15, 0,0, DEFAULT_TIME_ZONE),
                disruption.FromDate);
        assertEquals(ZonedDateTime.of(2017, 11, 8, 16, 0, 0, 0, DEFAULT_TIME_ZONE),
                disruption.ToDate);
    }

    private String getFileWithUtil(String fileName) {

        String result = "";

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
