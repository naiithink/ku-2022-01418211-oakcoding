package ku.cs.oakcoding.app.services.data_source.CSV;

import java.io.File;
import java.io.IOException;

public class SuspendedListCSV implements DataSourceCSV<>{
    private String directoryName;
    private String fileName;

    public SuspendedListCSV(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }

        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }








}
