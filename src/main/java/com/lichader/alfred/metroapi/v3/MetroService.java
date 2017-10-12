package com.lichader.alfred.metroapi.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lichader.alfred.metroapi.v3.model.DisruptionsResponse;
import com.lichader.alfred.metroapi.v3.model.RouteTypesResponse;
import com.lichader.alfred.metroapi.v3.model.RouteResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public abstract class MetroService {

    @Autowired
    protected MetroServiceURLBuilder urlBuilder;

    public MetroService() {

    }

    protected  <T> T getResource(String resourceName, Class<T> resourceType) {
        OkHttpClient client = new OkHttpClient();

        try {
            String routeTypeUrl = urlBuilder.buildURL(resourceName);
            Request request = new Request.Builder().url(routeTypeUrl).build();

            Response response = client.newCall(request).execute();

            String jsonString = response.body().string();

            ObjectMapper mapper = new ObjectMapper();
            T typed =  mapper.readValue(jsonString, resourceType);

            return typed;
        } catch (Exception ex){

            // print exception here
            return null;
        }

    }

}
