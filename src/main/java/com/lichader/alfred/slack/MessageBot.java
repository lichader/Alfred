package com.lichader.alfred.slack;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageBot {

    @Autowired
    private SlackAPIURLBuilder urlBuilder;

    public void send(String text){

        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder().url(urlBuilder.buildMessageURL(text)).build();

        try (Response response = httpClient.newCall(request).execute())
        {
            if (!response.isSuccessful()){
                System.out.println("Failed to send message.");
            }
        }
        catch (IOException ex){
            System.out.println("Failed to send message.");
        }
    }


}
