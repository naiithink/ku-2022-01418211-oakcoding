package ku.cs.oakcoding.app.helpers.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class OakFileTraverser
        extends SimpleFileVisitor<Path> {

    private static OakFileTraverser walker = new OakFileTraverser();

    private List<String> directoryContent;

    public OakFileTraverser() {
        this.directoryContent = new ArrayList<>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        directoryContent.add(file.toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        directoryContent.add(dir.toString());
        return FileVisitResult.CONTINUE;
    }

    public List<String> listDirectoryContent(Path dir) throws IOException {
        walker.directoryContent.clear();

        Files.walkFileTree(dir, walker);
        return walker.directoryContent;
    }

    public static void main(String[] args) throws IOException {
        OakFileTraverser v = new OakFileTraverser();

        for (String entry : v.listDirectoryContent(Paths.get("/Users/naiithink/projects/Gen/oakcoding/src/main/java/ku/cs/oakcoding/app/services"))) {
            System.out.println(entry);
        }
    }
}
