package com.jeesite.modules.Util;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

//消费着线程
public class Work01 {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(),"UTF-8");
           try {
               Thread.sleep(1000);
           }catch (Exception e){

           }
            System.out.println(message);
            //应答哪个消息，是否批量应答
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
        //取消消费的一个回调接口 如在消费的时候队列被删除掉了
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag+"消息消费被中断");
        };
        System.out.println("c1...........");
        channel.basicQos(1);//默认0为公平分发，1为不公平分发
        //信道名称，是否自动应答 接收到消息执行，中断消息执行
        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
    }
}
