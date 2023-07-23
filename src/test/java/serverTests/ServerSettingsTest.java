package serverTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.pack.ServerSettings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerSettingsTest {
   @Test
    public void getSettingsFromJsonTest() throws IOException {
        String path ="src/test/resources/serverTestDir", fileName ="serverSettingsTest.json";
        ServerSettings settings;
        int expectedPort = 1234, resultPort;
        String jsonStr = String.format("{\"port\":%d}",expectedPort);
        Files.writeString(Path.of(path,fileName),jsonStr, StandardCharsets.UTF_8);

        settings = ServerSettings.getSettingsFromJson(path, fileName);
        resultPort = settings.getPort();

        Assertions.assertEquals(expectedPort, resultPort);
    }
}
