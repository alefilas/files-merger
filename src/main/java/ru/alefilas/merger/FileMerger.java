package ru.alefilas.merger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileMerger {

    public static Path merge(Path root) throws IOException {

        if (!Files.isDirectory(root)) {
            throw new IOException("It's not a directory or directory does not exist: " + root);
        }

        List<Path> files = findAllTxtFiles(root);

        Path mergedFile = Path.of(root + "/merged-file.txt");

        for (Path file : files) {
            Files.write(mergedFile, Files.readAllBytes(file), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        }

        return mergedFile;
    }

    private static List<Path> findAllTxtFiles(Path root) throws IOException {
        return Files.walk(root)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".txt"))
                .sorted(Comparator.comparing(Path::getFileName))
                .collect(Collectors.toList());
    }
}
