package ku.cs.oakcoding.app.helpers.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class OakDirEditor extends SimpleFileVisitor<Path> {

    private static OakDirEditor walker = new OakDirEditor();

    private static ResursiveDeleteVisitor resursiveDeleteVisitor = walker.new ResursiveDeleteVisitor();

    private class ResursiveDeleteVisitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            System.err.println(exc);
            return FileVisitResult.CONTINUE;
        }
    }

    public static boolean deleteDirectory(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            throw new NotDirectoryException(path.toString());
        }

        Files.walkFileTree(path, resursiveDeleteVisitor);

        return true;
    }
}
