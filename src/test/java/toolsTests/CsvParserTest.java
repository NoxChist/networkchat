package toolsTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import tools.CsvParser;

import java.io.File;
import java.util.List;

public class CsvParserTest {
    public static class ParserTest {
        int i;
        boolean bool;

        public ParserTest() {
        }

        public ParserTest(int i, boolean bool) {
            this.i = i;
            this.bool = bool;
        }

        public int getI() {
            return i;
        }

        public boolean getBool() {
            return bool;
        }

        public void setI(int i) {
            this.i = i;
        }

        public void setBool(boolean bool) {
            this.bool = bool;
        }
    }

    private File csvFile = new File("src/test/resources/csvParserTestDir/csvTest.csv");
    private static int testCnt = 0;

    @AfterEach
    public void incrementCnt() {
        testCnt++;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csvParserTestDir/csvTest.csv")
    public void parseTest(int expectedI, boolean expectedBool) {
        CsvParser<ParserTest> parser = new CsvParser<>(csvFile);
        List<ParserTest> parseResult;
        int actualI;
        boolean actualBool;

        parseResult = parser.parse(ParserTest.class, new String[]{"i", "bool"});
        actualI = parseResult.get(testCnt).getI();
        actualBool = parseResult.get(testCnt).getBool();

        Assertions.assertEquals(expectedI, actualI);
        Assertions.assertEquals(expectedBool, actualBool);
    }
}
