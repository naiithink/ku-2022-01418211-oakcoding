package ku.cs.oakcoding.app.services.data_source.csv;

import ku.cs.oakcoding.app.models.picture.ProfileImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PictureRefactoring implements DataSourceCSV{

    private final String dirName = "data";
    private final String subDirName = "users";
    private String fileFolder;

    private final String fileName = "profile.jpg";

    public PictureRefactoring(String fileFolder) {
        this.fileFolder = fileFolder;
        checkFileIsExisted(dirName,MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + subDirName,MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + subDirName + File.separator + fileFolder,MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + subDirName + File.separator + fileFolder + File.separator + fileName,MakeFileType.FILE);

    }

    /**
     * @todo Use API?
     *       <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html>
     *       - Files::exists(Path, LinkOption...)
     *       - Files::createDirectory(Path, FileAttribute<?>...); FileAttribute<?> can be omitted
     */
    private void checkFileIsExisted(String filePath, MakeFileType makeFileType) {

        if (makeFileType == MakeFileType.DIRECTORY) {

            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }

        }

        if (makeFileType == MakeFileType.FILE) {
            File file = new File(filePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    @Override
    public Object readData() {
        ProfileImage profileImage = new ProfileImage();
        return profileImage;
    }

    @Override
    public void writeData(Object o) throws IOException {
        Path oldPath = (Path) o;
        String path = dirName + File.separator + subDirName + File.separator + fileFolder + File.separator + fileName;
        Path newPath = Paths.get(path);
        Files.copy(oldPath,newPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void clearData() {

    }
}
