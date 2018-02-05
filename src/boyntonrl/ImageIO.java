package boyntonrl;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is responsible for loading and saving images
 */
public class ImageIO {

    /**
     * Reads in the specified image file and returns a javafx.scene.image.Image object containing
     * the image.
     * @param file the specified image file
     * @return
     * @throws IOException
     */
    public Image read(File file) throws IOException{
        imageFilePath = selectedFile.getPath();
        dotIndex = imageFilePath.lastIndexOf(".");
        extension = imageFilePath.substring(dotIndex);
        if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".gif")) { // Java supported image format (.jpg,.png,.gif)
            try (FileInputStream stream = new FileInputStream(selectedFile)){
                image = new Image(stream);
                imageView.setImage(image);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (extension.equals(".msoe")) {

        }
        return;
    }

    /**
     *
     * @param image
     * @param file
     * @throws IOException
     */
    public void write(Image image, File file) throws IOException{
        // TODO
    }

    private Image readMSOE(File file) throws IOException{
        // TODO
    }

    private void writeMSOE(Image image, File file) throws IOException{
        // TODO
    }

    private Image readBMSOE(File file) throws IOException{
        // TODO
    }

    private void writeBMSOE(Image image, File file) throws IOException{
        // TODO
    }

}
