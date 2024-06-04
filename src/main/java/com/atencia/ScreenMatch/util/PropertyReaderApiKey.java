package com.atencia.ScreenMatch.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/application.properties")
public class PropertyReaderApiKey {

    @Autowired
    private Environment environment;

    public String getApiKey() {
        return environment.getProperty("API_KEY");
    }

    public String getApiKeyUrl() {
        return environment.getProperty("API_KEY_URL");
    }

}
