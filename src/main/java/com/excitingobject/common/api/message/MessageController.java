package com.excitingobject.common.api.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@RestController
@RequestMapping(value = "test")
public class MessageController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public Object test(@RequestParam String code) throws Exception {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());

    }
}
