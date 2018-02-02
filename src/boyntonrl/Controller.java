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
    private String imageFilePath;

    @FXML
    private ImageView imageView;

    @FXML
    private void open(ActionEvent e) {
        Image image;
        int dotIndex;
        String extension;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png",
                        "*.jpg", "*.gif", "*.msoe","*.bmsoe"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imageFilePath = selectedFile.getPath();
            dotIndex = imageFilePath.lastIndexOf(".");
            extension = imageFilePath.substring(dotIndex);
            System.out.println(extension);
            if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".gif")) { // Java supported image format (.jpg,.png,.gif)
                try (FileInputStream stream = new FileInputStream(selectedFile)){
                    image = new Image(stream);
                    imageView.setImage(image);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if (extension.equals(".msoe")) {

            }
        }
    }

//    @FXML
//    private void

}
