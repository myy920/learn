package com.myy;

import java.util.Timer;
import java.util.TimerTask;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        // 主线程执行一个睡眠任务
        // 添加一个守护线程, 每2秒打印一下
        funA();
        Thread.sleep(1000 * 10);
        flag = false;
    }

    private static Boolean flag = true;

    private static void funA() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (flag) {
                    System.out.println("A");
                }else {
                    timer.cancel();
                    timer.cancel();
                }
            }
        };
        new Timer().schedule(timerTask, 0, 1000);

    }

}
