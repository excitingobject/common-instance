package com.excitingobject.common.api.sequences;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@EntityScan(basePackages = "com.excitingobject")
@EnableJpaRepositories(basePackages = "com.excitingobject")
public class SeqConfig {
}
