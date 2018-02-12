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
    /**
     * Logger for Lab8 application. Logs to the text file "Lab8.txt"
     */
    public static final Logger LOGGER = Logger.getLogger(ImageIO.class.getName());
    private static Path path = Paths.get("Lab8.txt");

    private static final int X_OFFSET = 1535;
    private static final int Y_OFFSET = 595;

    private static final int PRIMARY_WIDTH = 500;
    private static final int PRIMARY_HEIGHT = 500;
    private static final int FILTER_WIDTH = 357;
    private static final int FILTER_HEIGHT = 191;


    @Override
    public void start(Stage primaryStage) throws Exception{

        setUpLogger();
        LOGGER.log(Level.INFO, "User opened application");

        FXMLLoader primaryLoader = new FXMLLoader();
        FXMLLoader kernelLoader = new FXMLLoader();

        Parent primaryRoot = primaryLoader.load(getClass().getResource("lab8.fxml").openStream());
        primaryStage.setTitle("Image Manipulator");
        primaryStage.setScene(new Scene(primaryRoot, PRIMARY_WIDTH, PRIMARY_HEIGHT));
        primaryStage.setResizable(false);

        Stage filterKernelStage = new Stage();
        Parent filterKernelRoot = kernelLoader.load(getClass().getResource("kernelUI.fxml")
                .openStream());
        filterKernelStage.setTitle("Filter Kernel");
        filterKernelStage.setScene(new Scene(filterKernelRoot, FILTER_WIDTH, FILTER_HEIGHT));
        filterKernelStage.setResizable(false);
        filterKernelStage.setX(X_OFFSET);
        filterKernelStage.setY(Y_OFFSET);

        PrimaryController primaryController = primaryLoader.getController();
        primaryController.setKernelStage(filterKernelStage);
        KernelController kernelController = kernelLoader.getController();
        kernelController.setStage(filterKernelStage);

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



}
