package boyntonrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class Controller {

//    public static Logger logger = new Logger("bla");
    private static String imageFilePath;
    private static String imageFileExtension;

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
        ImageIO.read(file);
    }

    public static String getImageFilePath() {
        return imageFilePath;
    }

    public static void setImageFilePath(String imageFilePath) {
        Controller.imageFilePath = imageFilePath;
    }

    public static String getImageFileExtension() {
        return imageFileExtension;
    }

    public static void setImageFileExtension(String imageFileExtension) {
        Controller.imageFileExtension = imageFileExtension;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    //    @FXML
//    private void

}
