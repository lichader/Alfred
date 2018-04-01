package com.lichader.alfred.util.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BSETrackingPageParser {

    public List<BSETrack> extractTrackingInfo(Document document) throws IOException{

        Elements results = document.select("#oTHtable tr");

        return results.stream().skip(1).map(o -> {
            return new BSETrack(o);
        }).sorted(Comparator.comparing(BSETrack::getTime)).collect(Collectors.toList());
    }

    public static class BSETrack{
        private LocalDateTime time;
        private String location;
        private String status;

        public BSETrack(Element element){
            Elements tds = element.select("td");

            String timeText = tds.get(0).text();
            time = LocalDateTime.parse(timeText, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            location = tds.get(1).text().replaceAll("[ |　]", " ").trim();
            status = tds.get(2).text().trim().replaceAll("　","").trim();
        }

        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
