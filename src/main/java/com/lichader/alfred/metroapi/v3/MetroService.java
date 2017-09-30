package com.lichader.alfred.metroapi.v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lichader.alfred.metroapi.v3.model.DisruptionsResponse;
import com.lichader.alfred.metroapi.v3.model.RouteTypesResponse;
import com.lichader.alfred.metroapi.v3.model.RouteResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lichader on 9/5/17.
 */
@Service
public class MetroService {
    public final static String HASH_ALGORITHM = "HmacSHA1";
    public final static String API_BASE_URL = "http://timetableapi.ptv.vic.gov.au";
    public final static String API_VERSION = "v3";
    public final static String RESOURCE_ROUTE_TYPES = "route_types";
    public final static String RESOURCE_ROUTES = "routes";
    public final static String RESOURCE_ALL_DISRUPTIONS = "disruptions";
    public final static String RESOURCE_SPECIFIC_ROUTE_DISRUP = "disruptions/route/";

    @Value("${metro.api.devid}")
    private String apiDeveloperId;

    @Value("${metro.api.key}")
    private String apiKey;

    public MetroService() {
    }


    public RouteTypesResponse getRouteTypes() throws Exception{
        OkHttpClient client = new OkHttpClient();

        String routeTypeUrl = buildURL(API_BASE_URL, API_VERSION, RESOURCE_ROUTE_TYPES);
        Request request = new Request.Builder().url(routeTypeUrl).build();

        Response response = client.newCall(request).execute();

        String jsonString = response.body().string();

        ObjectMapper mapper = new ObjectMapper();
        RouteTypesResponse typed =  mapper.readValue(jsonString, RouteTypesResponse.class);

        return typed;
    }

    public RouteResponse getRoutes() throws Exception{
        OkHttpClient client = new OkHttpClient();

        String routesUrl = buildURL(API_BASE_URL, API_VERSION, RESOURCE_ROUTES);
        Request request = new Request.Builder().url(routesUrl).build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        ObjectMapper mapper = new ObjectMapper();
        RouteResponse typed = mapper.readValue(jsonString, RouteResponse.class);

        return typed;
    }

    public DisruptionsResponse getAllDisruptions() throws Exception{
        OkHttpClient client = new OkHttpClient();

        String disruptionUrl = buildURL(API_BASE_URL, API_VERSION, RESOURCE_ALL_DISRUPTIONS);
        Request request = new Request.Builder().url(disruptionUrl).build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        ObjectMapper mapper = new ObjectMapper();
        DisruptionsResponse typed = mapper.readValue(jsonString, DisruptionsResponse.class);

        return typed;
    }

    public DisruptionsResponse getDisruption(int routeId) throws Exception{
        OkHttpClient client = new OkHttpClient();

        String resource = RESOURCE_SPECIFIC_ROUTE_DISRUP + routeId;
        String disruptionUrl = buildURL(API_BASE_URL, API_VERSION, resource);
        Request request = new Request.Builder().url(disruptionUrl).build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        ObjectMapper mapper = new ObjectMapper();
        DisruptionsResponse typed = mapper.readValue(jsonString, DisruptionsResponse.class);

        return typed;
    }



    public String buildURL( final String baseURL,
                            final String version,
                            final String resource) throws Exception {
        String signature = calculateSignature(version, resource);

        StringBuffer url = new StringBuffer(baseURL).append("/").
                        append(version).append("/").
                        append(resource).
                        append(resource.contains("?") ? "&" : "?").
                        append("devid=" + apiDeveloperId).
                        append("&signature=" + signature.toString().toUpperCase());

        return url.toString();
    }

    public String calculateSignature(final String version,
                                     final String resource) throws NoSuchAlgorithmException, InvalidKeyException{

        StringBuffer uriWithDeveloperID = new StringBuffer("/").
                append(version).append("/").
                append(resource).
                append(resource.contains("?") ? "&" : "?").
                append("devid=" + apiDeveloperId);

        byte[] keyBytes = apiKey.getBytes();
        byte[] uriBytes = uriWithDeveloperID.toString().getBytes();
        Key signingKey = new SecretKeySpec(keyBytes, HASH_ALGORITHM);
        Mac mac = Mac.getInstance(HASH_ALGORITHM);
        mac.init(signingKey);
        byte[] signatureBytes = mac.doFinal(uriBytes);

        StringBuffer signature = new StringBuffer(signatureBytes.length * 2);

        for (byte signatureByte : signatureBytes) {
            int intVal = signatureByte & 0xff;

            if (intVal < 0x10)
            {
                signature.append("0");
            }

            signature.append(Integer.toHexString(intVal));
        }

        return signature.toString().toUpperCase();
    }

}
