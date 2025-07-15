package hotelsystem.dependencies.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {
    private final Map<String, Properties> loadedConfigs = new HashMap<>();


    private Properties loadConfig(String fileName) {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream(fileName)) {
            if (input != null) {
                props.load(input);
            } else {
                System.err.println("Config file not found: " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Error loading config: " + fileName + ", " + e.getMessage());
        }
        return props;
    }

    public String getProperty(String configFileName, String propertyName) {
        Properties props = loadedConfigs.computeIfAbsent(configFileName, this::loadConfig);
        return props.getProperty(propertyName);
    }
}