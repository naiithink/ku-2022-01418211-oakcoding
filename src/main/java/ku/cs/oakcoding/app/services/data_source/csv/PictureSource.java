package ku.cs.oakcoding.app.services.data_source.csv;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.users.ProfileImageState;
import ku.cs.oakcoding.app.models.users.User;
import ku.cs.oakcoding.app.services.FactoryDataSourceCSV;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PictureSource implements DataSourceCSV<Path>{

    private final String dirName = "data";
    private final String subDirName = "users";
    private String fileFolder;

    private final String fileName = "profile.jpg";

    public PictureSource(String fileFolder) {
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
    public Path readData() {
        DataSourceCSV<User> userDataSourceCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER_PROFILE,fileFolder);
        User user = userDataSourceCSV.readData();
        if (user.getProfileImageState() == ProfileImageState.CUSTOM){
            String path = dirName + File.separator + subDirName + File.separator + fileFolder + File.separator + fileName;
            return Paths.get(path);
        }

        String path = "src/main/resources/images/DefaultProfile.jpg";
        return Paths.get(path);

    }

    @Override
    public void writeData(Path oldPath) throws IOException {
        String path = dirName + File.separator + subDirName + File.separator + fileFolder + File.separator + fileName;
        Path newPath = Paths.get(path);
        Files.copy(oldPath,newPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void clearData() {
        String path = dirName + File.separator + subDirName + File.separator + fileFolder + File.separator + fileName;
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e){
            System.err.println(e.getMessage());
        }

    }
}
