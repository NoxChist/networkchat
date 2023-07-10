package serverTests;

import client.ClientSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import server.ServerSettings;

public class ServerSettingsTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/serverTestDir/serverSettingsTest.csv")
    public void getSettingsFromCsvTest(int expectedPort) {
        int resultPort;
        ServerSettings settings;

        settings = ServerSettings.getSettingsFromCsv("src/test/resources/serverTestDir", "serverSettingsTest.csv");
        resultPort = settings.getPort();

        Assertions.assertEquals(expectedPort, resultPort);
    }
}
