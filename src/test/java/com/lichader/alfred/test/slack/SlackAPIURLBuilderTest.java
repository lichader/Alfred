package com.lichader.alfred.test.slack;

import com.lichader.alfred.slack.SlackAPIURLBuilder;
import org.junit.Assert;
import org.junit.Test;

public class SlackAPIURLBuilderTest {

    private String slackPostMessageURL = "https://slack.com/api/chat.postMessage";
    private String token = "dummy";
    private String channel = "general";
    private String text = "Hello World";

    private SlackAPIURLBuilder subject;

    public SlackAPIURLBuilderTest() {
        subject = new SlackAPIURLBuilder();
        subject.setPostMessageUrl(slackPostMessageURL);
        subject.setToken(token);
        subject.setChannel(channel);
    }

    @Test
    public void buildURL_expectCorrect(){
        String actual = subject.buildMessageURL(text);
        Assert.assertEquals("https://slack.com/api/chat.postMessage?token=dummy&channel=general&as_user=true&text=Hello World", actual);
    }
}
