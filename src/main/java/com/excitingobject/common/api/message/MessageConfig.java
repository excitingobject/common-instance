package com.excitingobject.common.api.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Slf4j
@AutoConfiguration
public class MessageConfig {

    @Value("${spring.messages.basename:classpath:/messages/message}")
    private String basename;

    @Bean
    @RefreshScope
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasenames(basename);
        ms.setDefaultEncoding("UTF-8");
        ms.setCacheSeconds(5);
        ms.setUseCodeAsDefaultMessage(true);
        return ms;
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageComponent messageComponent(MessageSource messageSource) {
        return new MessageComponent(messageSource);
    }
}