/**
 * @file PictureSourceCSV.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services.data_source.csv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import ku.cs.oakcoding.app.models.picture.Picture;

/**
 * PictureSourceCSV
 * 
 * @todo Verbose name 'directoryName' -> 'dirName'
 */
public class PictureSourceCSV implements DataSourceCSV<Picture> {

    private String directoryName;

    private String filename;

    private String picturePath;

    public PictureSourceCSV(String directoryName) {
        this.directoryName = directoryName;
        checkFileIsExisted();

    }

    /**
     * @todo Use API?
     *       <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html>
     *       - Files::exists(Path, LinkOption...)
     *       - Files::createDirectory(Path, FileAttribute<?>...); FileAttribute<?> can be omitted
     */
    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public Picture readData() {
        Picture picture = new Picture(picturePath, filename);
        return picture;
    }

    /**
     * @todo Use try-with-resources?
     * @todo ?: 'iis' -> 'in' since there is only
     * @todo Hard coding
     * @todo Image resources can also be manipulated via static methods of java.nio.file.Files, as same as regular files
     */
    @Override
    public void writeData(Picture pictureFrom) {
        BufferedImage image = null;
        if (!pictureFrom.getPicturePath().equals("picture/Default.jpg")) {
            File inputFile = new File(pictureFrom.getPicturePath());
            try {
                image = ImageIO.read(inputFile);

                ImageInputStream iis = ImageIO.createImageInputStream(inputFile);
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);

                if (!iter.hasNext()) {
                    throw new RuntimeException("No readers found!");
                }

                ImageReader reader = iter.next();
                String formatName = reader.getFormatName();
                if (formatName.equals("jpeg") || formatName.equals("jpg") || formatName.equals("JPEG")) {
                    ImageIO.write(image, "jpg", new File("picture/" + pictureFrom.getFileName() + ".jpg"));
                    this.picturePath = "picture/" + pictureFrom.getFileName() + ".jpg";
                    this.filename = pictureFrom.getFileName();
                    iis.close();
                } else if (formatName.equals("png")) {
                    ImageIO.write(image, "png", new File("picture/" + pictureFrom.getFileName() + ".png"));
                    this.picturePath = "picture/" + pictureFrom.getFileName() + ".png";
                    this.filename = pictureFrom.getFileName();
                    iis.close();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            this.picturePath = "picture/Default.jpg";
            this.filename = "Default";
        }
    }

    /**
     * @todo Hard coding
     */
    @Override
    public void clearData() {
        File picture = new File(picturePath);
        if (picture.exists() && !picturePath.equals("picture/Default.jpg")) {
            picture.delete();
        }
        this.picturePath = "picture/Default.jpg";
    }
}