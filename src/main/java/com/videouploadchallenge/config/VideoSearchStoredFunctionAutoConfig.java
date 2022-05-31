package com.videouploadchallenge.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
@Slf4j
public class VideoSearchStoredFunctionAutoConfig {
    @Autowired
    DataSource dataSource;

    @Value("classpath:stored-function/videoSearch.sql")
    private Resource videoSearchFunction;

    @PostConstruct
    public void runStoredProcedures() {
        log.info("Running video search stored function .......");

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false,false, "utf-8", videoSearchFunction);
        resourceDatabasePopulator.setSeparator(ScriptUtils.EOF_STATEMENT_SEPARATOR);

        resourceDatabasePopulator.execute(dataSource);
    }
}
