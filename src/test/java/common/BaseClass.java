package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


public class BaseClass {

    public static ExtentTest test;
    public static ExtentReports extent;

    protected static String BASE_URL = "";
    protected static final String AVAIABLE_STATUS = "available";

    @BeforeSuite
    public void before() {
        extent = new ExtentReports();

        // Specify the path to the YAML configuration file
        String configFilePath = "src/main/resources/config/config.yaml";

        // Read the YAML file and load it into a Map
        Map<String, String> configMap = readYamlFile(configFilePath);

        // Get the API URL from the configuration
        BASE_URL = configMap.get("apiUrl");

    }

    @BeforeMethod
    public void setUp(ITestResult result) throws Exception {
        test = extent.createTest(result.getMethod().getMethodName());

    }

    @AfterSuite
    public void tearDownSuite() {
        //extent.flush();
    }


    public void reportLog(String message) {
        test.log(Status.INFO, message);

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
