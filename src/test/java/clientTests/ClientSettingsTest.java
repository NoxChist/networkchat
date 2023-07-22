package clientTests;

import client.pack.ClientSettings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ClientSettingsTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/clientTestDir/clientTestSettings.csv")
    public void getSettingsFromCsvTest(String expectedHost, int expectedPort) {
        String resultHost;
        int resultPort;
        ClientSettings settings;

        settings = ClientSettings.getSettingsFromCsv("src/test/resources/clientTestDir", "clientTestSettings.csv");
        resultHost = settings.getHost();
        resultPort = settings.getPort();

        Assertions.assertEquals(expectedHost, resultHost);
        Assertions.assertEquals(expectedPort, resultPort);
    }
}
