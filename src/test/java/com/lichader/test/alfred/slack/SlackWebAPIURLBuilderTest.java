package com.lichader.test.alfred.slack;

import com.lichader.alfred.slack.SlackWebAPIURLBuilder;
import org.junit.Assert;
import org.junit.Test;

public class SlackWebAPIURLBuilderTest {

    private String slackPostMessageURL = "https://slack.com/api/chat.postMessage";
    private String token = "dummy";
    private String channel = "general";
    private String text = "Hello World";

    private SlackWebAPIURLBuilder subject;

    public SlackWebAPIURLBuilderTest() {
        subject = new SlackWebAPIURLBuilder();
        subject.setPostMessageUrl(slackPostMessageURL);
        subject.setToken(token);
        subject.setChannel(channel);
    }

    @Test
    public void expectCorrectURL(){
        String actual = subject.buildMessageURL(text);

        Assert.assertEquals("https://slack.com/api/chat.postMessage?token=dummy&channel=general&as_user=true&text=Hello World", actual);
    }
}
