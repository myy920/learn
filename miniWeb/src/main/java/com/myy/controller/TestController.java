package com.myy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TestController {


    @GetMapping("/t1")
    String t1() {
        return "t1";
    }

    @GetMapping("/t2")
    String t2() {
        return "t2";
    }

    @GetMapping("time")
    String time(HttpServletRequest request, HttpServletResponse response) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String cookie = request.getHeader("Cookie");
        response.setHeader("Set-Cookie", "time=" + time + "; " + cookie);
        return time;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(miniWebTime());
    }

    public static String miniWebTime() {
        try {
            URL url = new URL("http://192.168.1.103:8080/time");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Cookie", "a=1; b=2; c=3");
            urlConnection.connect();
            return urlConnection.getHeaderField("Set-Cookie");
        } catch (IOException e) {
            return null;
        }
    }

}
