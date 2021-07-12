package com.jeesite.modules.Util;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

public class Task01 {

    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.confirmSelect();//开启发布确认
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);//队列持久化
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());//消息持久化
            boolean flag = channel.waitForConfirms();//发布确认是否确认
            System.out.println("发送消息完成"+message);
        }


    }
}
