package tools;

import java.io.File;

public class PathBuilder {
    public static boolean build(String path) {
        String[] nods;
        if (path.contains("\\")) {
            path = path.replace('\\', '/');
        }
        nods = path.split("/");
        StringBuilder pathBuild = new StringBuilder();
        File file;

        for (int i = 0; i < nods.length; i++) {
            pathBuild.append(nods[i]);
            file = new File(pathBuild.toString());

            if (!file.exists()) {
                file.mkdir();
            }
            pathBuild.append("/");
        }
        return new File(path).exists();
    }
}
