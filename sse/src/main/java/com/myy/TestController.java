package com.myy;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/sse")
    SseEmitter sse() {
        SseEmitter sseEmitter = new SseEmitter(0L);
        // 异步调用sseEmitter对象发送消息
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    String message = i + " second...";
                    System.out.println(message);
                    sseEmitter.send(message);
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        return sseEmitter;
    }


}
