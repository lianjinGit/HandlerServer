package com.handle.resoureManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.handle.constants.Constants;

public class RescourceManager {
    /**
    * Logger for this class
    */
    private static final Logger logger = Logger.getLogger(RescourceManager.class);

    private static RescourceManager INSTANCE;

    private static Properties resourceProperties;

    public static RescourceManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RescourceManager();
            INSTANCE.load();
        }
        return INSTANCE;
    }

    private void load() {
        resourceProperties = new Properties();
        URL uri = RescourceManager.class.getClassLoader().getResource(Constants.CONFIG_FILE);
        try {
            InputStream inputStream = uri.openStream();
            resourceProperties.load(inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error("load config file occurrence error", e);
            System.exit(0);
        }
    }

    public String getProperty(String key) {
        return resourceProperties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        String value = resourceProperties.getProperty(key);
        return value != null ? value : defaultValue;
    }

    public static void main(String[] args) {
        System.out.println(getInstance().getProperty("test"));
    }

}
