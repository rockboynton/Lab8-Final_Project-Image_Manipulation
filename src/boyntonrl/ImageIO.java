package boyntonrl;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.IllegalFormatException;

/**
 * This class is responsible for loading and saving images
 */
public class ImageIO {

    /**
     * Reads in the specified image file and returns a javafx.scene.image.Image object containing
     * the image.
     * @param file the specified image file
     * @return
     */
    public static Image read(File file) {
        Image image;
        String path = file.getPath();
        String extension = path.substring(path.lastIndexOf("."));
        Controller.setImageFilePath(path);
        Controller.setImageFileExtension(extension);
        if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".gif")) { // Java supported image format (.jpg,.png,.gif)
//            try (FileInputStream stream = new FileInputStream(file)){
//                image = new Image(stream);
//            } catch (IOException e1) {
//                e1.printStackTrace(); // TODO make alert
//            }
            image = new Image(file.toURI().toString());
        } else if (extension.equals(".msoe")) {
            readMSOE(file);
        }
        return image;
    }

    /**
     *
     * @param image
     * @param file
     * @throws IOException
     */
    public static void write(Image image, File file) {
        // TODO
    }

    private static Image readMSOE(File file) {
        // TODO
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1 && !line.equals("MSOE")) {
                    throw new IOException();
                } else if
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeMSOE(Image image, File file) {
        // TODO
    }

//    private static Image readBMSOE(File file) throws IOException{
//        // TODO
//    }

    private static void writeBMSOE(Image image, File file) {
        // TODO
    }

}
