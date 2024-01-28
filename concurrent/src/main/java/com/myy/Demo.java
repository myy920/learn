package com.myy;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        MessageQueue messageQueue = new MessageQueue();
        new Thread(() -> {
            log.info("准备消费消息");
            messageQueue.get(message -> {
                log.info("消费者: {}", message);
            });
        }, "consumer").start();

        Thread.sleep(3000);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                messageQueue.set("prod_" + finalI + "产生的消息");
            }, "prod" + i).start();
        }

    }


}

class MessageQueue {

    private static final List<Object> messages = new ArrayList<>();

    public static final Object lock = new Object();

    public void get(Consumer<Object> consumer) {
        synchronized (lock) {
            while (true) {
                if (messages.size() > 0) {
                    messages.removeIf(message -> {
                        consumer.accept(message);
                        return true;
                    });
                }
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void set(Object message) {
        synchronized (lock) {
            messages.add(message);
            lock.notifyAll();
        }
    }

}
