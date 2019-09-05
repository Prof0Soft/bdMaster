package utils;

import java.util.ResourceBundle;

/**
 * Read properties of file.
 */
public final class ReadProperties {

    private static final String PROPERTIES_NAME_CONNECTION_DB = "settings";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(PROPERTIES_NAME_CONNECTION_DB);

    private ReadProperties() {
    }

    /**
     * Method return value of properties by properties.
     *
     * @param nameProperties - name properties.
     * @return value of properties.
     */
    public static String getProperties(final String nameProperties) {
        return resourceBundle.getString(nameProperties);
    }
}
