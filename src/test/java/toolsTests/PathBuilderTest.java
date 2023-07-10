package toolsTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.PathBuilder;

import java.io.File;

public class PathBuilderTest {
    File dir = null;

    @AfterEach
    public void deletePath() {
        if (dir != null && dir.exists()) {
            File parent = dir.getParentFile();
            dir.delete();
            parent.delete();
        }
    }

    @Test
    public void buildTest() {
        String path = "src/test/pathBuilderTestDir/dir";
        dir = new File(path);
        boolean beforeBuildExpectation = false;
        boolean afterBuildExpectation = true;
        boolean beforeBuildRes, afterBuildRes;

        beforeBuildRes = dir.exists();
        PathBuilder.build(path);
        afterBuildRes = dir.exists();

        Assertions.assertEquals(beforeBuildExpectation, beforeBuildRes);
        Assertions.assertEquals(afterBuildExpectation, afterBuildRes);
    }
}
