package serverTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import server.pack.ServerLogger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.util.Date;

import static org.mockito.Mockito.mock;

public class ServerLoggerTest {
    @Test
    public void getInstanceTest() throws IOException {
        Class<ServerLogger> expectedClass  = ServerLogger.class;
        ServerLogger instance;

        instance = ServerLogger.getInstance();
        instance.close();

        Assertions.assertNotNull(instance);
        Assertions.assertInstanceOf(expectedClass,instance);
    }
    @Test
    public void logTest(@TempDir Path tempDir) throws IOException {
        Path tempDirPath = tempDir.resolve("serverLoggerTest.log");
        ServerLogger.setDefaultServerLoggerSettings(tempDirPath.toString(),"yyyy");
        ServerLogger instance = ServerLogger.getInstance();
        String level = "[TEST]", msg ="logger test",
        expectedStr = Year.now().toString()+level+"#"+msg+"\n", actualStr;

        instance.log(level,new Date(),msg );
        actualStr = Files.readString(tempDirPath);
        instance.close();

        Assertions.assertEquals(expectedStr,actualStr);
    }


}
