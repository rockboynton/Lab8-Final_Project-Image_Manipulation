package boyntonrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {

    @FXML
    private ImageView imageView;

    @FXML
    private void open(ActionEvent e) {
        Image image;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        try (FileInputStream stream = new FileInputStream(selectedFile)){
            image = new Image(stream);
            imageView.setImage(image);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
