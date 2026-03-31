package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetry = 2; // total tries = 3
    int count = 0;
    int maxRetry = 2;  // retry 2 times

    @Override
    public boolean retry(ITestResult result) {

        Throwable cause = result.getThrowable();

        //Do NOT retry assertion failures
        if (cause instanceof AssertionError) {
            return false;
        }

        if (retryCount < maxRetry) {
            retryCount++;
            System.out.println("Retrying test: "
                    + result.getName() + " | Attempt: " + retryCount);
            return true;
        }

        if (count < maxRetry) {
            count++;
            System.out.println("Retrying test: " + result.getName() + " | Attempt: " + count);
            return true;
        }
        return false;
    }
}