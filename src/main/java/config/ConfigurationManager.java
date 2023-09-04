package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private static ConfigurationManager configurationManager = null;
    private Properties properties;

    private ConfigurationManager(String filePath) {
        super();
        try (InputStream input = getClass().getResourceAsStream(filePath)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void init(String filePath) {
        configurationManager = new ConfigurationManager(filePath);
    }

    public static ConfigurationManager getInstance() {
        return configurationManager;
    }

    public String getProperty(String emailHost) {
        return properties.getProperty(emailHost);
    }
}
