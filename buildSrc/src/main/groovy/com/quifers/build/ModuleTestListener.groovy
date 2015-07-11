import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult

import java.util.concurrent.atomic.AtomicLong

class ModuleTestListener implements TestListener {

    private AtomicLong delay = new AtomicLong(0);
    private static final String GRADLE_SUITE_NAME = "Gradle suite"
    private final String projectName;

    ModuleTestListener(String projectName) {
        this.projectName = projectName
    }

    @Override
    void beforeSuite(TestDescriptor testDescriptor) {
        if (GRADLE_SUITE_NAME.equals(testDescriptor.name)) {
            println "Starting Test Suite For Project [${projectName}]."
        }
    }

    @Override
    void afterSuite(TestDescriptor testDescriptor, TestResult testResult) {
        if (GRADLE_SUITE_NAME.equals(testDescriptor.name)) {
            println("Test Suite completed for ${projectName} in ${(testResult.endTime - testResult.startTime)} milliseconds.")
            println("Total Test(s): ${testResult.testCount} executed in ${delay} milliseconds.")
            println("Successful: ${testResult.successfulTestCount}, Failed: ${testResult.failedTestCount}, Skipped: ${testResult.skippedTestCount}")
        }
    }

    @Override
    void beforeTest(TestDescriptor testDescriptor) {
    }

    @Override
    void afterTest(TestDescriptor testDescriptor, TestResult testResult) {
        delay.addAndGet(testResult.endTime - testResult.startTime)
    }

}
