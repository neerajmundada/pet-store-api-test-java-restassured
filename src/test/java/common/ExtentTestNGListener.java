package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener {

    private static ExtentReports extent = new ExtentReports();
    private static ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReport.html");
        BaseClass.extent.attachReporter(htmlReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
       // test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        BaseClass.test.pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseClass.test.fail("Test failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        BaseClass.test.skip("Test skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        BaseClass.test.pass("Test passed with min success %");
    }

    @Override
    public void onFinish(ITestContext context) {
        BaseClass.extent.flush();
    }

}
