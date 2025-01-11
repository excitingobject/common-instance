package com.excitingobject.common.mq;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Map;

public class MqComponent {
    private RabbitTemplate rabbitTemplate;
    private Queue instanceQueue;
    public MqComponent(RabbitTemplate rabbitTemplate, Queue instanceQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.instanceQueue = instanceQueue;
    }

    public void send(String exchangeName, String routingKey, String message) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
    }

    public void send(Exchange exchange, Object data) {
        String exchangeName = exchange.getName();
        MqMessage message = new MqMessage(data);
        send(exchangeName, exchangeName, message.getMessage());
    }

    public void send(Queue queue, Object data) {
        String queueName = queue.getName();
        MqMessage message = new MqMessage(data);
        send(null, queueName, message.getMessage());
    }

    @RabbitListener(queues = "#{instanceQueue.getName()}")
    public void setInstanceListener(String message) {
        try {
            MqMessage<Map> mqMessage = new MqMessage(message, Map.class);
            Map data = mqMessage.getData();
//            log.info("[Instance:" + data.getProcessName() + "] Connect Time : " + data.getConnectTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
