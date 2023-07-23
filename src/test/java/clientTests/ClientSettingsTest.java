package clientTests;

import client.pack.ClientSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientSettingsTest {

    @Test
    public void getSettingsFromJsonTest() throws IOException {
        String path ="src/test/resources/clientTestDir", fileName ="clientTestSettings.json";
        ClientSettings settings;
        String expectedHost = "ClientSettingsTest", resultHost;
        int expectedPort = 1234, resultPort;
        String jsonStr = String.format("{\"host\":\"%s\",\"port\":%d}",expectedHost,expectedPort);
        Files.writeString(Path.of(path,fileName),jsonStr, StandardCharsets.UTF_8);


        settings = ClientSettings.getSettingsFromJson(path, fileName);
        resultHost = settings.getHost();
        resultPort = settings.getPort();

        Assertions.assertEquals(expectedHost, resultHost);
        Assertions.assertEquals(expectedPort, resultPort);
    }
}
