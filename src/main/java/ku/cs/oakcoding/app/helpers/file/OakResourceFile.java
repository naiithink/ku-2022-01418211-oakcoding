package ku.cs.oakcoding.app.helpers.file;

import java.nio.file.Path;
import java.util.ArrayList;

public class OakResourceFile extends OakFile {
    
    private ArrayList<String> externalPrefixFilePathStringArrayList;

    protected OakResourceFile(ArrayList<String> filePathStringArrayList,
                              Path filePath,
                              String fileDigestString) {
        super(filePathStringArrayList,
                filePath,
                fileDigestString);
    }

    @Override
    public ArrayList<String> getFilePathStringArrayList() {
        ArrayList<String> absolute
        return new ArrayList<String>();
    }
}
