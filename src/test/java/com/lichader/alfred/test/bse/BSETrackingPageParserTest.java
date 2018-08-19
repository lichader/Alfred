package com.lichader.alfred.test.bse;

import com.lichader.alfred.bse.BSETrackingPageParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BSETrackingPageParserTest {

    private BSETrackingPageParser subject = new BSETrackingPageParser();

    @Test
    public void parseHtmlFile_expectThreeReturns() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File htmlFile = new File(classLoader.getResource("example1.html").getFile());

        Document document = Jsoup.parse(htmlFile, "GB2312", "http://example.com/");

        List<BSETrackingPageParser.BSETrack> result = subject.extractTrackingInfo(document);

        assertEquals(7, result.size());

        int first = 0;
        assertEquals(LocalDateTime.of(2018, 1, 14, 11, 28), result.get(first).getTime());
        assertEquals("岳阳中心", result.get(first).getLocation());
        assertEquals("离开【 岳阳中心 】，下一站【平江】", result.get(first).getStatus());

        int last = result.size()-1;
        assertEquals(LocalDateTime.of(2018, 1, 14, 19, 37), result.get(last).getTime());
        assertEquals("平江县投递部", result.get(last).getLocation());
        assertEquals("【 平江县投递部 】已妥投", result.get(last).getStatus());
    }
}
