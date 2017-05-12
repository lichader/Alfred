package com.lichader.alfred.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lichader.alfred.service.model.RouteTypesResponse;
import com.lichader.alfred.service.model.RoutesResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    private final String PROP_METRO_DEV_ID = "metro.api.devid";
    private final String PROP_METRO_KEY = "metro.api.key";

    private final int DEVELOPER_ID;
    private final String API_KEY;
    public final static String HASH_ALGORITHM = "HmacSHA1";
    public final static String API_BASE_URL = "http://timetableapi.ptv.vic.gov.au";
    public final static String API_VERSION = "v3";
    public final static String RESOURCE_ROUTE_TYPES = "route_types";
    public final static String RESOURCE_ROUTES = "routes";

    public MetroService() {
        System.out.println("Metro Service is starting.");

        DEVELOPER_ID = Integer.valueOf(System.getProperty(PROP_METRO_DEV_ID));
        API_KEY = System.getProperty(PROP_METRO_KEY);
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

    public RoutesResponse getRoutes() throws Exception{
        OkHttpClient client = new OkHttpClient();

        String routesUrl = buildURL(API_BASE_URL, API_VERSION, RESOURCE_ROUTES);
        Request request = new Request.Builder().url(routesUrl).build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        ObjectMapper mapper = new ObjectMapper();
        RoutesResponse typed = mapper.readValue(jsonString, RoutesResponse.class);

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
                        append("devid=" + DEVELOPER_ID).
                        append("&signature=" + signature.toString().toUpperCase());

        return url.toString();
    }

    public String calculateSignature(final String version,
                                     final String resource) throws NoSuchAlgorithmException, InvalidKeyException{

        StringBuffer uriWithDeveloperID = new StringBuffer("/").
                append(version).append("/").
                append(resource).
                append(resource.contains("?") ? "&" : "?").
                append("devid=" + DEVELOPER_ID);

        byte[] keyBytes = API_KEY.getBytes();
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
