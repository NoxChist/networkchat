package clientTests;

import client.pack.ClientLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.util.Date;
import java.util.List;

public class ClientLoggerTest {

    @Test
    public void logOneArgTest(@TempDir Path tempDir) throws IOException {
        Path tempFilePath = tempDir.resolve("loggerTestFile.log");
        ClientLogger logger = new ClientLogger(tempFilePath.toString(),"yyyy");
        String expectedStr ="Logger one arg test str";
        List<String> strFromFile;
        int expectedSize = 1;
        int actualSize;
        String actualStr;

        logger.log(expectedStr);
        strFromFile = Files.readAllLines(tempFilePath);
        actualSize = strFromFile.size();
        actualStr = strFromFile.get(0);
        logger.close();

        Assertions.assertEquals(expectedSize,actualSize);
        Assertions.assertEquals(expectedStr,actualStr);
    }

    @Test
    public void log2argsTest(@TempDir Path tempDir) throws IOException {
        Path tempFilePath = tempDir.resolve("loggerTestFile.log");
        ClientLogger logger = new ClientLogger(tempFilePath.toString(),"yyyy");
        String msg = "ClientLoggerTest";
        String expectedStr =String.format("***%s в %s***", msg, Year.now().getValue());
        List<String> strFromFile;
        int expectedSize = 1;
        int actualSize;
        String actualStr;

        logger.log(new Date(),msg);
        strFromFile = Files.readAllLines(tempFilePath);
        actualSize = strFromFile.size();
        actualStr = strFromFile.get(0);
        logger.close();

        Assertions.assertEquals(expectedSize,actualSize);
        Assertions.assertEquals(expectedStr,actualStr);
    }
}
