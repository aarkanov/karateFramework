package karate.rest.soap.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.intuit.karate.Results;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import net.masterthought.cucumber.Configuration;
import karate.rest.soap.testing.DateTimeUtility;
import karate.rest.soap.testing.ReportUtility;

public class ReportUtilityTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testConstructor() {
        ReportUtility reportUtility = new ReportUtility();

        assertNotNull(reportUtility);
    }

    @Test
    public void testParallelDefault() {
        Results results = ReportUtility.testParallel();

        assertEquals(1, results.getThreadCount());
        assertEquals("src/test/reports/karate-output", results.getReportDir());
    }

    @Test
    public void testParallelWithParameter() {
        int threadCount = 10;

        Results results = ReportUtility.testParallel(threadCount);

        assertEquals(threadCount, results.getThreadCount());
        assertEquals("src/test/reports/karate-output", results.getReportDir());
    }

    @Test
    public void testJsonPathsCreation() throws IOException {
        File firstFile = folder.newFile("FirstFile.json");
        File secondFile = folder.newFile("SecondFile.json");
        Results results = Results.startTimer(1);
        results.setReportDir(folder.getRoot().getAbsolutePath());
        List<String> expectedFiles = new ArrayList<String>(2);
        expectedFiles.add(firstFile.getAbsolutePath());
        expectedFiles.add(secondFile.getAbsolutePath());

        List<String> actualFiles = ReportUtility.getJsonFiles(results);

        assertThat(actualFiles, CoreMatchers.is(expectedFiles));
    }

    @Test
    public void testReportGeneration() throws IOException {
        File secondFolder = folder.newFolder("karate-output");
        Results results = Results.startTimer(1);
        results.setReportDir(secondFolder.getAbsolutePath());
    
        ReportUtility.generateReport(results);

        String[] directories = folder.getRoot().list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        
        assertEquals(directories + " does not contain 2 elements", 2, directories.length);
        assertTrue("karate-output is not in the list", Arrays.asList(directories).contains("karate-output"));
        
        int index = Arrays.asList(directories).indexOf("karate-output");
        int dateIndex = index == 1 ? 0 : 1;
        
        assertEquals("Date/time report folder is not in the list", 19, directories[dateIndex].length());
        assertEquals("Date/time report folder is not in the list", DateTimeUtility.getFormattedDate(), 
            directories[dateIndex].substring(0, directories[dateIndex].length() - 9));
    }

    @Test
    public void testConfigCreation() {
        File reportDirectory = new File("src/report");
        String projectName = "project";
        Configuration expectedConfiguration = new Configuration(reportDirectory, projectName);

        Configuration actualConfiguration = ReportUtility.createConfiguration(reportDirectory, projectName);

        assertEquals(expectedConfiguration.getReportDirectory(), actualConfiguration.getReportDirectory());
        assertEquals(expectedConfiguration.getProjectName(), actualConfiguration.getProjectName());
    }
}