package common;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class ConfigReader {

    public static void main(String[] args) {
        // Specify the path to the YAML configuration file
        String configFilePath = "path/to/config.yaml";

        // Read the YAML file and load it into a Map
        Map<String, String> configMap = readYamlFile(configFilePath);

        // Get the API URL from the configuration
        String apiUrl = configMap.get("apiUrl");

        System.out.println("API URL: " + apiUrl);
    }

    private static Map<String, String> readYamlFile(String filePath) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            // Load the YAML file into a Map
            return yaml.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
