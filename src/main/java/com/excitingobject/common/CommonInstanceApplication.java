package com.excitingobject.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootApplication
public class CommonInstanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonInstanceApplication.class, args);
    }

}
