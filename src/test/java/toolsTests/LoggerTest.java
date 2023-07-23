package toolsTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.util.Date;
import java.util.List;

public class LoggerTest {
    public class LoggerChild extends Logger {
        protected LoggerChild(String path, String dateFormatPattern) throws IOException {
            super(path, dateFormatPattern);
        }
    }
    private String dateFormatPattern = "yyyy";
    private String logMsg = "Это тест логера.";
    private String name = "LoggerTest";

    @Test
    public void logTest(@TempDir Path tempDir) throws IOException {
        Path tempFilePath = tempDir.resolve("loggerTestFile.log");
        LoggerChild logger = new LoggerChild(tempFilePath.toString(), dateFormatPattern);
        Date date = new Date();
        int yyyy = Year.now().getValue();
        String fileLogStrExpectation = yyyy + " LoggerTest: Это тест логера.";

        List<String> strFromFile;
        int expectedSize = 1;
        int actualSize;
        String actualStr;

        logger.log(date, name, logMsg);
        strFromFile = Files.readAllLines(tempFilePath);
        actualSize = strFromFile.size();
        actualStr = strFromFile.get(0);
        logger.close();
        Files.deleteIfExists(tempFilePath);

        Assertions.assertEquals(expectedSize,actualSize);
        Assertions.assertEquals(fileLogStrExpectation,actualStr);
    }

    @Test
    public void logAppendTest(@TempDir Path tempDir) throws IOException {
        Path tempFilePath = tempDir.resolve("loggerTestFile.log");
        LoggerChild logger = new LoggerChild(tempFilePath.toString(), dateFormatPattern);
        Date date = new Date();
        int yyyy = Year.now().getValue();
        String fileLogStrExpectation = yyyy + " LoggerTest: Это тест логера.";
        List<String> strFromFile;
        int expectedSize = 2;
        int actualSize;
        String actualStr1, actualStr2;

        logger.log(date, name, logMsg);
        logger.log(date, name, logMsg);

        strFromFile = Files.readAllLines(tempFilePath);
        actualSize = strFromFile.size();
        actualStr1 = strFromFile.get(0);
        actualStr2 = strFromFile.get(1);
        logger.close();

        Assertions.assertEquals(expectedSize,actualSize);
        Assertions.assertEquals(fileLogStrExpectation,actualStr1);
        Assertions.assertEquals(fileLogStrExpectation,actualStr2);
    }
}
