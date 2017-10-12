package com.lichader.alfred.metroapi.v3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Service
public class MetroServiceURLBuilder {

    public final static String HASH_ALGORITHM = "HmacSHA1";
    public final static String API_BASE_URL = "http://timetableapi.ptv.vic.gov.au";
    public final static String API_VERSION = "v3";


    @Value("${metro.api.devid}")
    private String apiDeveloperId;

    @Value("${metro.api.key}")
    private String apiKey;

    public String buildURL(final String resource) throws Exception {
        String signature = calculateSignature(resource);

        StringBuffer url = new StringBuffer(API_BASE_URL).append("/").
                append(API_VERSION).append("/").
                append(resource).
                append(resource.contains("?") ? "&" : "?").
                append("devid=" + apiDeveloperId).
                append("&signature=" + signature.toString().toUpperCase());

        return url.toString();
    }

    public String calculateSignature(final String resource) throws NoSuchAlgorithmException, InvalidKeyException {

        StringBuffer uriWithDeveloperID = new StringBuffer("/").
                append(API_VERSION).append("/").
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


    public void setApiDeveloperId(String apiDeveloperId) {
        this.apiDeveloperId = apiDeveloperId;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
