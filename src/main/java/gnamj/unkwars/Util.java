package gnamj.unkwars;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class Util {

    public static void deleteDirectory(@NotNull File dir) {
        for (File content : Objects.requireNonNull(dir.listFiles())) {
            if (content.isDirectory()) deleteDirectory(content);
            else content.delete();
        }
    }
}
