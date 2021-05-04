import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.alefilas.merger.FileMerger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileMergerTest {

    private final List<Path> dirs = List.of(
            Path.of("dir1"),
            Path.of("dir1/dir2"),
            Path.of("dir1/dir3")
    );

    private final List<Path> files = List.of(
            Path.of("dir1/file3.txt"),
            Path.of("dir1/dir2/file2.txt"),
            Path.of("dir1/dir3/file1.txt")
    );

    @Before
    public void create() throws IOException {

        for (Path dir : dirs) {
            Files.createDirectory(dir);
        }

        for (Path file : files) {
            Files.createFile(file);
        }

        for (int i = 0; i < files.size(); i++) {
            Files.writeString(files.get(i), "file" + (files.size() - i));
        }
    }

    @After
    public void delete() throws IOException {

        for (Path file : files) {
            Files.deleteIfExists(file);
        }

        Files.deleteIfExists(Path.of("dir1/merged-file.txt"));

        for (int i = dirs.size() - 1; i >= 0; i--) {
            Files.deleteIfExists(dirs.get(i));
        }
    }

    @Test
    public void mergeTest() throws IOException {

        Path mergedFile = FileMerger.merge(dirs.get(0));
        Assert.assertEquals("dir1\\merged-file.txt", mergedFile.toString());

        String data = Files.readString(mergedFile);
        Assert.assertEquals("file1file2file3", data);
    }

    @Test(expected = IOException.class)
    public void directoryIsFileTest() throws IOException {
        FileMerger.merge(files.get(0));
    }

    @Test(expected = IOException.class)
    public void directoryDoesNotExistTest() throws IOException {
        FileMerger.merge(Path.of(dirs.get(0) + "\\dir"));
    }
}
