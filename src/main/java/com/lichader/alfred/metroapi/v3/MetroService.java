package com.lichader.alfred.metroapi.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lichader.alfred.util.SerializationHelperFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.Optional;

public abstract class MetroService {

    private final Logger logger = LoggerFactory.getLogger(MetroService.class);

    @Autowired
    protected MetroServiceURLBuilder urlBuilder;

    public MetroService() {

    }

    protected  <T> Optional<T> getResource(String resourceName, Class<T> resourceType) {
        OkHttpClient client = new OkHttpClient();

        try {
            String routeTypeUrl = urlBuilder.buildURL(resourceName);
            Request request = new Request.Builder().url(routeTypeUrl).build();

            if (logger.isDebugEnabled()){
                logger.debug("Start retrieving resource {}", resourceName);
            }
            Response response = client.newCall(request).execute();

            String jsonString = response.body().string();

            ObjectMapper mapper = SerializationHelperFactory.getHelper();
            T typed =  mapper.readValue(jsonString, resourceType);

            logger.info("Sucesfully retreived resource: {}", resourceName);

            return Optional.of(typed);
        } catch (Exception ex){
            logger.error("Received an error while retrieving resource: " + resourceName, ex);

            // print exception here
            return Optional.empty();
        }

    }

}
