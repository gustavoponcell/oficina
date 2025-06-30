package com.mycompany.oficina;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/** Utility base class that sets a temporary working directory with a copy
 * of the data folder so CRUD tests don't alter the repository files. */
public abstract class TestBase {
    private Path tempDir;
    private String originalDir;

    @BeforeEach
    void setup() throws IOException {
        originalDir = System.getProperty("user.dir");
        tempDir = Files.createTempDirectory("oficina-test-");
        Path src = Paths.get(originalDir, "data");
        Path dest = tempDir.resolve("data");
        copyRecursive(src, dest);
        System.setProperty("user.dir", tempDir.toString());
    }

    @AfterEach
    void cleanup() throws IOException {
        System.setProperty("user.dir", originalDir);
        try (Stream<Path> walk = Files.walk(tempDir)) {
            walk.sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException e) {
                        // ignore cleanup errors
                    }
                });
        }
    }

    private static void copyRecursive(Path src, Path dest) throws IOException {
        Files.createDirectories(dest);
        try (Stream<Path> stream = Files.walk(src)) {
            for (Path path : (Iterable<Path>) stream::iterator) {
                Path relative = src.relativize(path);
                Path target = dest.resolve(relative.toString());
                if (Files.isDirectory(path)) {
                    Files.createDirectories(target);
                } else {
                    Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
