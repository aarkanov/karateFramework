package slalom.karate.framework;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import org.apache.commons.io.FileUtils;
import slalom.karate.framework.DateTimeUtility;;

public class ReportUtility {
    public Results testParallel() {
        return testParallel(1, "src/test/reports/karate-output");
    }

    public Results testParallel(int threads) {
        return testParallel(threads, "src/test/reports/karate-output");
    }

    public Results testParallel(int threads, String reportDirectory) {
        Results results = Runner.parallel(getClass(), threads, reportDirectory);
        return results;
    }
    
    public void generateReport(Results results) {
        ReportBuilder reportBuilder = new ReportBuilder(getJsonFiles(results),
            createConfiguration(new File(results.getReportDir().replace("karate-output", DateTimeUtility.getFormattedDateTime())), "demo"));
        reportBuilder.generateReports();
    }

    public Configuration createConfiguration(File reportDirectory, String projectName) {
        return new Configuration(reportDirectory, projectName);
    }

    public List<String> getJsonFiles(Results results) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(results.getReportDir()), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<String>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        return jsonPaths;
    }
}