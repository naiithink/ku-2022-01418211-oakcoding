package ku.cs.oakcoding.app.helpers.file;

import ku.cs.oakcoding.app.helpers.security.OakDigest;

import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

public class OakFile {

    private ArrayList<String> filePathStringArrayList;

    private Path filePath;

    private String fileDigestString;

    /**
     * Use @ref OakFiles factory methods
     */
    protected OakFile(ArrayList<String> filePathStringArrayList,
                      Path filePath,
                      String fileDigestString) {
        this.filePathStringArrayList = filePathStringArrayList;
        this.filePath = filePath;
        this.fileDigestString = fileDigestString;
    }

    public void updateDigest() throws FileNotFoundException {
        OakDigest.getDigestStringOf(filePath);
    }

    public ArrayList<String> getFilePathStringArrayList() {
        return filePathStringArrayList;
    }

    public String getFilePathString() {
        return String.join(FileSystems.getDefault().getSeparator(), filePathStringArrayList);
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getFileDigestString() {
        return fileDigestString;
    }
}
