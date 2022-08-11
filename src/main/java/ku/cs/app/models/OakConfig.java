package ku.cs.app.models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.io.BufferedReader;

class OakConfig {

    private final String APP_CONFIG_FILE_DIR = "src/resources/ku.cs/";
    private final String APP_CONFIG_FILE_NAME = "oakconfig.properties";
    private BufferedReader appConfigFileBuffer;
    private Properties appConfigProperties;

    OakConfig() {
        try {
            appConfigFileBuffer =
                    new BufferedReader(
                    new FileReader(APP_CONFIG_FILE_DIR + APP_CONFIG_FILE_NAME)
            );

            appConfigProperties.load(appConfigFileBuffer);
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }
}
