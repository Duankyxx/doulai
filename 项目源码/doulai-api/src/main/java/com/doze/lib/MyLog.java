package com.doze.lib;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class MyLog {
    //手写日志框架

    //日志内容
    private BlockingDeque<String> stringBlockingDeque = new LinkedBlockingDeque<>();
    private LogThread logThread;
    //日志保存位置
    private static String filePath = "log.txt";


    public MyLog() {
        logThread = new LogThread();
        new Thread(logThread).start();
    }

    public void info(String log, Integer level) {
        //将日志存放到缓存
        writeLog(log, level);
    }

    private void writeLog(String log, Integer level) {
        String lev = "";
        switch (level) {
            case 0:
                lev = "错误:";
                break;
            case 1:
                lev = "警告:";
                break;
            case 2:
                lev = "信息:";
                break;
            case 3:
                lev = "提示:";
                break;
            default:
                System.out.println("传参错误!");
                return;
        }//2022-10-10 错误:  -->  log
        String msg = getDateTime() + "  " + lev + "  -->  " + log;
        stringBlockingDeque.offer(msg);
    }

    private String getDateTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        return dateFormat.format(date);
    }

    //内核态转成用户态
    class LogThread implements Runnable {
        @Override
        public void run () {
            File file = new File(filePath);
            StringBuilder log = new StringBuilder();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file,true);

                while (true) {
                    Thread.sleep(5000);
                    int size = stringBlockingDeque.size();
                    if (size == 0) continue;
//                    System.out.println(Thread.currentThread().getName() + "写入");
                    log.setLength(0);
                    for (int i=0; i < size; ++i) {
                        //取出队列
                        log.append(stringBlockingDeque.poll() + "\r\n");
                    }
                    //批量写入
                    fileOutputStream.write(new String(log).getBytes());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
