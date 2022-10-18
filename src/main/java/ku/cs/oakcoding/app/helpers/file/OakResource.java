package ku.cs.oakcoding.app.helpers.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.configurations.OakSystemDefaults;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public final class OakResource {

    private static final Path DATA_DIR = OakResourcePrefix.getDataDirPrefix();

    private static OakFileTraverser walker = new OakFileTraverser();

    public static final int fixDataDirectoryMissingNodes() throws IOException {
        if (!Files.exists(DATA_DIR)) {
            if (!OakResource.restoreDataDirectory(true))
                OakResource.hardRestoreDataDirectory();
        }

        int recoveredCount = 0;

        // Expected
        List<String> expectedTraverseResult = walker.listDirectoryContent(OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_INSTALL_DIR_TEMPLATE.value()));
        List<String> filteredExpectedTraverseResult = new ArrayList<>();

        String expectedRootDirectory = expectedTraverseResult.get(expectedTraverseResult.size() - 1) + OakSystemDefaults.FILE_SEPARATOR.value();

        Iterator<String> expectedTraverseResultIter = expectedTraverseResult.iterator();
        while (expectedTraverseResultIter.hasNext()) {
            String entry = expectedTraverseResultIter.next();
            filteredExpectedTraverseResult.add(entry.replaceFirst(expectedRootDirectory, ""));
        }

        filteredExpectedTraverseResult.remove(filteredExpectedTraverseResult.size() - 1);


        // Actual
        List<String> actualTraverseResult = walker.listDirectoryContent(DATA_DIR);
        List<String> filteredActualTraverseResult = new ArrayList<>();

        String actualRootDirectory = actualTraverseResult.get(actualTraverseResult.size() - 1) + OakSystemDefaults.FILE_SEPARATOR.value();

        Iterator<String> actualTraverseResultIter = actualTraverseResult.iterator();
        while (actualTraverseResultIter.hasNext()) {
            String entry = actualTraverseResultIter.next();
            filteredActualTraverseResult.add(entry.replaceFirst(actualRootDirectory, ""));
        }

        filteredActualTraverseResult.remove(filteredActualTraverseResult.size() - 1);

        // Perform fixing
        Collections.reverse(filteredActualTraverseResult);
        Collections.reverse(filteredExpectedTraverseResult);

        for (String entry : filteredExpectedTraverseResult) {
            if (!filteredActualTraverseResult.contains(entry)) {
                Files.copy(OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_INSTALL_DIR_TEMPLATE.value()).resolve(entry), DATA_DIR.resolve(entry));
                recoveredCount++;
            }
        }

        return recoveredCount;
    }

    public static final boolean restoreDataDirectory(boolean override) throws IOException {
        if (Files.exists(DATA_DIR) && override)
            OakDirEditor.deleteDirectory(DATA_DIR);

        Files.copy(OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_INSTALL_DIR_TEMPLATE.value()), DATA_DIR, StandardCopyOption.REPLACE_EXISTING);

        return true;
    }

    public static final boolean hardRestoreDataDirectory() {
        try {
            OakDirEditor.deleteDirectory(DATA_DIR);
        } catch (IOException e) {
            OakLogger.log(Level.WARNING, "Got 'IOException' while attempting to delete directory");
        }

        try {
            Files.createDirectories(OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_INSTALL_DIR.value()));

            for (String dir : OakAppDefaults.DEFAULT_DATA_DIR.get("dir"))
                Files.createDirectories(DATA_DIR.resolve(dir));

            for (String file : OakAppDefaults.DEFAULT_DATA_DIR.get("file")) {
                Files.deleteIfExists(DATA_DIR.resolve(file));
                Files.createFile(DATA_DIR.resolve(file));
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static Path renameFile(Path renameFrom, Path renameTo) throws IOException {
        return Files.move(renameFrom, renameFrom.resolveSibling(renameTo));
    }

    public static Path copyFile(Path source, Path target) throws IOException {
        return Files.copy(source, target);
    }

    public static int recursiveCopyDirectory(Path source, Path target) throws IOException {
        if (!Files.exists(source)) {
            throw new FileNotFoundException(source.toString());
        }

        int copiedCount = 0;
        List<String> dirContent = walker.listDirectoryContent(source);

        Collections.reverse(dirContent);

        dirContent.remove(0);

        for (String content : dirContent) {
            String fileName = content.replaceFirst(source.toString() + OakSystemDefaults.FILE_SEPARATOR.value(), "");

            if (Files.isDirectory(Paths.get(content)))
                Files.createDirectories(target.resolve(fileName));
            else if (Files.isRegularFile(Paths.get(content)))
                Files.copy(Paths.get(content), target.resolve(fileName));

            copiedCount++;
        }

        return copiedCount;
    }

    public static void main(String[] args) throws IOException {
        OakResource.fixDataDirectoryMissingNodes();
    }
}
