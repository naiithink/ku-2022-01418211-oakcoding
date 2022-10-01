package ku.cs.oakcoding.app.services.data_source;

import ku.cs.oakcoding.app.models.picture.Picture;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public class PictureSourceCSV implements DataSourceCSV<Picture> {

    private String directoryName;
    private String filename;
    private String picturePath;

    public PictureSourceCSV(String directoryName){
        this.directoryName = directoryName;
        checkFileIsExisted();

    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public Picture readData() {
        Picture picture = new Picture(picturePath,filename);
        return picture;
    }

    @Override
    public void writeData(Picture pictureFrom) {
        BufferedImage image = null;
        if (!pictureFrom.getPicturePath().equals("picture/Default.jpg")){
            File input_file = new File(pictureFrom.getPicturePath());
            try {
                image = ImageIO.read(input_file);

                ImageInputStream iis = ImageIO.createImageInputStream(input_file);
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);

                if (!iter.hasNext()) {
                    throw new RuntimeException("No readers found!");
                }

                ImageReader reader = iter.next();
                String formatName = reader.getFormatName();
                if (formatName.equals("jpeg") || formatName.equals("jpg") || formatName.equals("JPEG")){
                    ImageIO.write(image, "jpg", new File("picture/"+pictureFrom.getFilename()+".jpg"));
                    this.picturePath = "picture/"+pictureFrom.getFilename()+".jpg";
                    this.filename = pictureFrom.getFilename();
                    iis.close();
                }
                else if (formatName.equals("png")){
                    ImageIO.write(image,"png", new File("picture/"+pictureFrom.getFilename()+".png"));
                    this.picturePath = "picture/"+pictureFrom.getFilename()+".png";
                    this.filename = pictureFrom.getFilename();
                    iis.close();
                }
            } catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        else {
            this.picturePath = "picture/Default.jpg";
            this.filename = "Default";
        }
    }

    @Override
    public void clearData() {
        File picture = new File(picturePath);
        if (picture.exists() && !picturePath.equals("picture/Default.jpg")) {
            picture.delete();
        }
        this.picturePath = "picture/Default.jpg";
    }
}
