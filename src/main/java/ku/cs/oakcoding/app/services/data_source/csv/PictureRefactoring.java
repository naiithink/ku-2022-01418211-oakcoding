package ku.cs.oakcoding.app.services.data_source.csv;

import ku.cs.oakcoding.app.models.picture.ProfileImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PictureRefactoring implements DataSourceCSV{

    private String dirName;
    private ProfileImage profileImage;

    public PictureRefactoring(String directoryName) {
        this.dirName = directoryName;
        checkFileIsExisted();

    }

    /**
     * @todo Use API?
     *       <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html>
     *       - Files::exists(Path, LinkOption...)
     *       - Files::createDirectory(Path, FileAttribute<?>...); FileAttribute<?> can be omitted
     */
    private void checkFileIsExisted() {
        File file = new File(dirName);
        if (!file.exists()) {
            file.mkdir();
        }
    }


    @Override
    public Object readData() {
        ProfileImage profileImage = new ProfileImage();
        return profileImage;
    }

    @Override
    public void writeData(Object o) throws IOException {
        ProfileImage profileImage = (ProfileImage) o;
        String path = "picture/" + profileImage.getFileName();
        Path newPath = Paths.get(path);
        Files.move(profileImage.getProFileImagePath(),newPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void clearData() {

    }
}
