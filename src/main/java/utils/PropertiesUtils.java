package utils;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtils {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        var inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);

    }

    private PropertiesUtils() {
    }
}
