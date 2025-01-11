package com.excitingobject.common.mq.rabbit;

import com.excitingobject.common.mq.MqComponent;
import com.excitingobject.common.utils.ConstUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@AutoConfiguration
public class RabbitMqConfig {


    @Value("${eo.instance.prefix:eo}")
    private String prefix;

    @Value("${spring.application.name:common}")
    private String appName;

    @Bean
    TopicExchange instanceExchange() {
        // 모든 인스턴스 큐에 바인딩되는 exchange
        return new TopicExchange(ConstUtils.joinText(prefix, "instance"));
    }

    @Bean
    Queue instanceQueue() {
        return new Queue(ConstUtils.joinText(prefix, appName), false, true, true);
    }

    @Bean
    Binding instanceQueueBinding(Queue instanceQueue, TopicExchange instanceExchange) {
        return BindingBuilder.bind(instanceQueue).to(instanceExchange).with(instanceExchange.getName());
    }

    @Bean
    MqComponent mqComponent(RabbitTemplate rabbitTemplate, Queue instanceQueue) {
        return new MqComponent(rabbitTemplate, instanceQueue);
    }

}
