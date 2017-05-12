package com.lichader.alfred.service;

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

    public final static int DEVELOPER_ID = 0;
    public final static String API_KEY = "";
    public final static String HASH_ALGORITHM = "HmacSHA1";
    public final static String API_BASE_URL = "http://timetableapi.ptv.vic.gov.au";
    public final static String API_VERSION = "v3";
    public final static String API_RESOURCE_ROUTE_TYPE = "route_types";

    public MetroService() {
        System.out.println("Metro Service is starting.");
    }


    public String getRouteTypes() throws Exception{
        OkHttpClient client = new OkHttpClient();

        String routeTypeUrl = buildURL(API_BASE_URL, API_VERSION, API_RESOURCE_ROUTE_TYPE, DEVELOPER_ID, API_KEY);
        Request request = new Request.Builder().url(routeTypeUrl).build();

        Response response = client.newCall(request).execute();

        String jsonString = response.body().string();

        return jsonString;
    }

    public String buildURL( final String baseURL,
                            final String version,
                            final String resource,
                            final int developerId,
                            final String privateKey) throws Exception {
        String signature = calculateSignature(version, resource, developerId, privateKey);

        StringBuffer url = new StringBuffer(baseURL).append("/").
                        append(version).append("/").
                        append(resource).
                        append(resource.contains("?") ? "&" : "?").
                        append("devid=" + developerId).
                        append("&signature=" + signature.toString().toUpperCase());

        return url.toString();
    }

    public String calculateSignature(final String version,
                                     final String resource,
                                     final int developerId,
                                     final String privateKey) throws NoSuchAlgorithmException, InvalidKeyException{

        StringBuffer uriWithDeveloperID = new StringBuffer("/").
                append(version).append("/").
                append(resource).
                append(resource.contains("?") ? "&" : "?").
                append("devid=" + developerId);

        byte[] keyBytes = privateKey.getBytes();
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
