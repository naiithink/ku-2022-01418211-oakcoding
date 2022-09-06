package ku.cs.oakcoding.app.services.Picture;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


public class PictureSourceCSV {

    private String pictureFolder;
    private String filename;
    private String pictureForm;
    private String picturePath;

    public PictureSourceCSV(String pictureFolder, String filename, String pictureFrom) {
        this.pictureFolder = pictureFolder;
        this.filename = filename;
        this.pictureForm = pictureFrom;
        checkFileIsExisted();

    }

    private void checkFileIsExisted() {
        File file = new File(pictureFolder);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public void writePicture(){

        BufferedImage image = null;
        if (!pictureForm.equals("picture/Default.jpg")){
            File input_file = new File(pictureForm);
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
                    ImageIO.write(image, "jpg", new File("picture/"+filename+".jpg"));
                    this.picturePath = "picture/"+filename+".jpg";
                    iis.close();
                }
                else if (formatName.equals("png")){
                    ImageIO.write(image,"png", new File("picture/"+filename+".png"));
                    this.picturePath = "picture/"+filename+".png";
                    iis.close();
                }
            } catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        else {
            this.picturePath = "picture/Default.jpg";
        }
    }

    public void deletePicture(String picturePath){
        File picture = new File(picturePath);
        if (picture.exists() && !picturePath.equals("picture/Default.jpg")) {
            picture.delete();
        }
        this.picturePath = "picture/Default.jpg";
    }

    public String getPicturePath(){
        return picturePath;
    }



}
