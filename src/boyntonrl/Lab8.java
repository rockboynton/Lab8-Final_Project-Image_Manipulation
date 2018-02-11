/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 8 Final Project - Image Manipulation
 * Name: Rock Boynton
 * Created: 2/1/18
 */

package boyntonrl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * JavaFX application to load, manipulate, and save images in .png, .gif, .jpg, .msoe, and .bmsoe
 * formats
 */
public class Lab8 extends Application {

    public static final Logger LOGGER = Logger.getLogger(ImageIO.class.getName());
    public static Path path = Paths.get("Lab8.txt");

    /**
     * Width of the primary stage
     */
    public static final int WIDTH = 500;
    /**
     * Height of the primary stage
     */
    public static final int HEIGHT = 500;

    private Stage filterKernelStage;
    private Controller kernelController;
    private Controller primaryController;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader primaryLoader = new FXMLLoader();
        FXMLLoader kernelLoader = new FXMLLoader();

        setUpLogger();
        LOGGER.log(Level.INFO, "User opened application");

        Parent primaryRoot = primaryLoader.load(getClass().getResource("lab8.fxml").openStream());
        primaryStage.setTitle("Image Manipulator");
        primaryStage.setScene(new Scene(primaryRoot, WIDTH, HEIGHT));

        Parent filterKernelRoot = kernelLoader.load(getClass().getResource("kernelUI.fxml")
                .openStream());
        filterKernelStage = new Stage();
        filterKernelStage.setTitle("Filter Kernel");
        filterKernelStage.setScene(new Scene(filterKernelRoot, WIDTH, HEIGHT));

        primaryController = primaryLoader.getController();
        kernelController = kernelLoader.getController();

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    private static void setUpLogger() {
        try {
            FileHandler fh = new FileHandler(path.toString());
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            Alert logFileAlert = new Alert(Alert.AlertType.ERROR, "Error with Log" +
                    " file ");
            logFileAlert.setTitle("Error Dialog");
            logFileAlert.setHeaderText("Log file error");
            logFileAlert.showAndWait();
        }
    }

    public Stage getFilterKernelStage() {
        return filterKernelStage;
    }
}
