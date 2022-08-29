package ku.cs.oakcoding.app.helpers.file;

import ku.cs.oakcoding.app.helpers.security.OakDigest;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Factory methods for OakFile
 */
public abstract class OakFiles {

    private OakFiles() {}

    public static OakFile newOakFile(String digestAlgorithm,
                                     String absolutePathString) throws FileNotFoundException {
        Path filePath = FileSystems.getDefault().getPath(absolutePathString);

        if (Files.exists(filePath) == false) {
            throw new FileNotFoundException(filePath.toString());
        }

        ArrayList<String> filePathStringArrayList = new ArrayList<String>(
                Arrays.asList(absolutePathString.split(FileSystems.getDefault().getSeparator()))
        );

        String fileDigestString = OakDigest.getDigestStringOf(filePath);

        return new OakFile(filePathStringArrayList, filePath, fileDigestString);
    }

    public static OakFile newOakFile(String absolutePathString) throws FileNotFoundException {
        return newOakFile(OakDigest.getDefaultDigestAlgorithm(),
                absolutePathString);
    }
}
