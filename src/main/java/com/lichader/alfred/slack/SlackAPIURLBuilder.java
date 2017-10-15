package com.lichader.alfred.slack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SlackAPIURLBuilder {

    @Value("${slack.postMessage.url}")
    private String postMessageUrl;

    @Value("${slack.token}")
    private String token;

    @Value("${slack.postMessage.channel}")
    private String channel;

    public String buildMessageURL(String messageText){
        StringBuilder sb = new StringBuilder(postMessageUrl).append("?");
        sb.append("token=").append(token).append("&");
        sb.append("channel=").append(channel).append("&");
        sb.append("as_user=").append("true").append("&");
        sb.append("text=").append(messageText);

        return sb.toString();
    }

    public String getPostMessageUrl() {
        return postMessageUrl;
    }

    public void setPostMessageUrl(String postMessageUrl) {
        this.postMessageUrl = postMessageUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
