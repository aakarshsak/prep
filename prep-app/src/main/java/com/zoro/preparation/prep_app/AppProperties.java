package com.zoro.preparation.prep_app;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private String environment;
    private String version;
    private boolean debug;
    private boolean mockExternalServices;
}
