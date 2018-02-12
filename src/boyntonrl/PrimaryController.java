/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 8 Final Project - Image Manipulation
 * Name: Rock Boynton
 * Created: 2/1/18
 */

package boyntonrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for the Lab8 JavaFX application to handle program logic.
 */
public class PrimaryController {

    private static final Logger LOGGER = Lab8.LOGGER;

    private Stage kernelStage;

    private Image image;
    private Image transformedImage;

    public void setKernelStage(Stage kernelStage) {
        this.kernelStage = kernelStage;
    }

    @FXML
    private ImageView imageView;
    @FXML
    private Button filterButton;

    @FXML
    private void open(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png",
                        "*.jpg", "*.gif", "*.msoe", "*.bmsoe"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            image = ImageIO.read(file);
            imageView.setImage(image);
        } else {
            LOGGER.log(Level.INFO, "User canceled loading image");
        }
    }

    @FXML
    private void save(ActionEvent e) {
        if (imageView.getImage() != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save New Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png",
                            "*.jpg", "*.gif", "*.msoe", "*.bmsoe"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                ImageIO.write(imageView.getImage(), file);
            }
        } else {
            // alert saying no image is loaded yet
            showImageNotLoadedAlert();
            // log
            LOGGER.log(Level.INFO, "User attempted to save when no image was present");

        }
    }

    @FXML
    private void reload(ActionEvent e) {
        imageView.setImage(image);
        transformedImage = image;
    }

    @FXML
    private void grayscale(ActionEvent e) {
        if (image != null) {
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
                    writer.setColor(x, y, grayPixel);
                }
            }
            transformedImage = newImage;
            imageView.setImage(transformedImage);
        }
    }

    @FXML
    private void red(ActionEvent e) {

    }

    @FXML
    private void redGray(ActionEvent e) {

    }

    @FXML
    private void negative(ActionEvent e) {
        if (image != null) {
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
                    negativePixel = pixel.invert();
                    writer.setColor(x, y, negativePixel);
                }
            }
            transformedImage = newImage;
            imageView.setImage(transformedImage);
        }
    }

    @FXML
    private void filter(ActionEvent e) {
        if (kernelStage.isShowing()) {
            kernelStage.close();
            filterButton.setText("Show Filter");
        } else {
            kernelStage.show();
            filterButton.setText("Hide Filter");
        }
    }

    private void showImageNotLoadedAlert() {
        Alert imageNotLoadedAlert = new Alert(Alert.AlertType.ERROR, "Error: No image " +
                "has been loaded in yet! ");
        imageNotLoadedAlert.setTitle("Error Dialog");
        imageNotLoadedAlert.setHeaderText("Image Not found");
        imageNotLoadedAlert.showAndWait();
    }


}
