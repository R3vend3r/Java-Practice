package hotelsystem.Utils;

import hotelsystem.dependencies.annotation.ConfigProperty;
import java.util.Properties;

public class HotelConfig {

    @ConfigProperty(propertyName = "hotel.room.status.change.enabled")
    private boolean roomStatusChangeEnabled = true;

    @ConfigProperty(propertyName = "hotel.room.history.entries.max")
    private int maxHistoryEntries = 3;

    @ConfigProperty(propertyName = "hotel.database.file")
    private String databaseFilePath = "hotel_db.json";

    @ConfigProperty(propertyName = "hotel.auto.save.enabled")
    private boolean autoSaveEnabled = true;

    private static HotelConfig instance;
    private Properties properties;

    private HotelConfig() {
    }

    public static synchronized HotelConfig getInstance() {
        if (instance == null) {
            instance = new HotelConfig();
            instance.loadProperties();
        }
        return instance;
    }

    private void loadProperties() {
        properties = new Properties();
        try {
            // Попробуем загрузить из файла в рабочей директории
            String externalConfig = System.getProperty("hotel.config.file", "hotel.properties");
            java.nio.file.Path path = java.nio.file.Paths.get(externalConfig);

            if (java.nio.file.Files.exists(path)) {
                try (java.io.InputStream input = java.nio.file.Files.newInputStream(path)) {
                    properties.load(input);
                }
            } else {
                // Загружаем из ресурсов
                try (java.io.InputStream input = getClass().getClassLoader()
                        .getResourceAsStream("hotel.properties")) {
                    if (input != null) {
                        properties.load(input);
                    }
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("⚠ Ошибка загрузки конфигурации: " + e.getMessage());
        }
    }

    // Геттеры
    public boolean isRoomStatusChangeEnabled() {
        return roomStatusChangeEnabled;
    }

    public int getMaxHistoryEntries() {
        return maxHistoryEntries;
    }

    public String getDatabaseFilePath() {
        return databaseFilePath;
    }

    public boolean isAutoSaveEnabled() {
        return autoSaveEnabled;
    }
}