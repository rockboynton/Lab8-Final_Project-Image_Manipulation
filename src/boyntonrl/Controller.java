package boyntonrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Controller {

    private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

    private static Image image;
    private static Image tranformedImage = image;


    @FXML
    private ImageView imageView;

    @FXML
    private void open(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png",
                        "*.jpg", "*.gif", "*.msoe","*.bmsoe"));
        File file = fileChooser.showOpenDialog(null);
        try {
            image = ImageIO.read(file);
            imageView.setImage(image);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (IndexOutOfBoundsException e2) {
            e2.printStackTrace(); // TODO add alert and log
        }
    }

    @FXML
    private void save(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save New Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png",
                        "*.jpg", "*.gif", "*.msoe","*.bmsoe"));
        File file = fileChooser.showSaveDialog(null);
        try {
            ImageIO.write(tranformedImage, file);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (IndexOutOfBoundsException e2) {
            e2.printStackTrace(); // TODO add alert and log
        }
    }

    @FXML
    private void reload(ActionEvent e) {
        imageView.setImage(image);
        tranformedImage = image;
    }

    @FXML
    private void grayscale(ActionEvent e) {
        WritableImage newImage;
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
         newImage = new WritableImage(width, height);
        PixelWriter writer = newImage.getPixelWriter();
        PixelReader reader = image.getPixelReader();
        Color grayPixel;
        Color pixel;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = reader.getColor(x, y);
                grayPixel = pixel.grayscale();
                writer.setColor(x,y, grayPixel);
            }
        }
        tranformedImage = newImage;
        imageView.setImage(tranformedImage);
    }

    @FXML
    private void red(ActionEvent e) {

    }

    @FXML
    private void redGray(ActionEvent e) {

    }

    @FXML
    private void negative(ActionEvent e) {
        WritableImage newImage;
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        newImage = new WritableImage(width, height);
        PixelWriter writer = newImage.getPixelWriter();
        PixelReader reader = image.getPixelReader();
        Color negativePixel;
        Color pixel;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel = reader.getColor(x, y);
//                double red = pixel.getRed();
//                double green = pixel.getGreen();
//                double blue = pixel.getBlue();
//                double opacity = pixel.getOpacity();
                negativePixel = pixel.invert();
                writer.setColor(x,y, negativePixel);
            }
        }
        tranformedImage = newImage;
        imageView.setImage(tranformedImage);
    }

    @FXML
    private void filter(ActionEvent e) {

    }

    //    @FXML
//    private void

}
