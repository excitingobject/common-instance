package com.excitingobject.common.api.message;

import com.excitingobject.common.api.response.EoResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Slf4j
public class MessageComponent {

    private MessageSource messageSource;

    public MessageComponent(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(EoResponseStatus status, String... args) {
        try {
            String key = "status." + status.getCode();
            return messageSource.getMessage(key, args, key, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

}
