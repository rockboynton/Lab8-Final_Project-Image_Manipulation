/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 8 Final Project - Image Manipulation
 * Name: Rock Boynton
 * Created: 2/1/18
 */

package boyntonrl;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import java.awt.image.Kernel;
import java.awt.image.BufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.ImagingOpException;

/**
 * Controller for the filter kernel stage.
 */
public class KernelController {

    private static final Logger LOGGER = Lab8.LOGGER;

    private Stage kernelStage;
    private PrimaryController primaryController;

    public void setStage(Stage kernelStage) {
        this.kernelStage = kernelStage;
    }

    @FXML
    private TextField topLeft;
    @FXML
    private TextField topMiddle;
    @FXML
    private TextField topRight;
    @FXML
    private TextField middleLeft;
    @FXML
    private TextField middleMiddle;
    @FXML
    private TextField middleRight;
    @FXML
    private TextField bottomLeft;
    @FXML
    private TextField bottomMiddle;
    @FXML
    private TextField bottomRight;
    @FXML
    private TextField divisor;
    @FXML
    private Slider kernelApplications;

    private int numKernelApplications = 0;

    private DecimalFormat format = new DecimalFormat("0.#");

    private static final double[] DEFAULT_BLUR = {
            0, 1, 0,
            1, 5, 1,
            0, 1, 0};

    private static final double[] DEFAULT_SHARPEN = {
            0, -1, 0,
            -1, 5, -1,
            0, -1, 0};

    private static final double[] DEFAULT_EDGE = {
            0, -1, 0,
            -1, 4, -1,
            0, -1, 0};

    @FXML
    private void blur(ActionEvent e) {
        // accessing DEFAULT_BLUR array to set text in appropriate fields
        topLeft.setText(format.format(DEFAULT_BLUR[0]));
        topMiddle.setText(format.format(DEFAULT_BLUR[1]));
        topRight.setText(format.format(DEFAULT_BLUR[2]));
        middleLeft.setText(format.format(DEFAULT_BLUR[3]));
        middleMiddle.setText(format.format(DEFAULT_BLUR[4]));
        middleRight.setText(format.format(DEFAULT_BLUR[5]));
        bottomLeft.setText(format.format(DEFAULT_BLUR[6]));
        bottomMiddle.setText(format.format(DEFAULT_BLUR[7]));
        bottomRight.setText(format.format(DEFAULT_BLUR[8]));
    }

    @FXML
    private void sharpen(ActionEvent e) {
        // accessing DEFAULT_BLUR array to set text in appropriate fields
        topLeft.setText(format.format(DEFAULT_SHARPEN[0]));
        topMiddle.setText(format.format(DEFAULT_SHARPEN[1]));
        topRight.setText(format.format(DEFAULT_SHARPEN[2]));
        middleLeft.setText(format.format(DEFAULT_SHARPEN[3]));
        middleMiddle.setText(format.format(DEFAULT_SHARPEN[4]));
        middleRight.setText(format.format(DEFAULT_SHARPEN[5]));
        bottomLeft.setText(format.format(DEFAULT_SHARPEN[6]));
        bottomMiddle.setText(format.format(DEFAULT_SHARPEN[7]));
        bottomRight.setText(format.format(DEFAULT_SHARPEN[8]));
    }

    @FXML
    private void edge(ActionEvent e) {
        // accessing DEFAULT_BLUR array to set text in appropriate fields
        topLeft.setText(format.format(DEFAULT_EDGE[0]));
        topMiddle.setText(format.format(DEFAULT_EDGE[1]));
        topRight.setText(format.format(DEFAULT_EDGE[2]));
        middleLeft.setText(format.format(DEFAULT_EDGE[3]));
        middleMiddle.setText(format.format(DEFAULT_EDGE[4]));
        middleRight.setText(format.format(DEFAULT_EDGE[5]));
        bottomLeft.setText(format.format(DEFAULT_EDGE[6]));
        bottomMiddle.setText(format.format(DEFAULT_EDGE[7]));
        bottomRight.setText(format.format(DEFAULT_EDGE[8]));
    }

    @FXML
    private void apply(ActionEvent e) {
        try {
            float[] kernel = new float[9];
            BufferedImage sourceImage;
            BufferedImage filteredImage = SwingFXUtils.fromFXImage(primaryController
                    .getImage(), null);
            Image newImage = primaryController.getImage();
            // fill the kernel array with appropriate values from text fields
            kernel[0] = Float.valueOf(topLeft.getText()) / Float.valueOf(divisor.getText());
            kernel[1] = Float.valueOf(topMiddle.getText()) / Float.valueOf(divisor.getText());
            kernel[2] = Float.valueOf(topRight.getText()) / Float.valueOf(divisor.getText());
            kernel[3] = Float.valueOf(middleLeft.getText()) / Float.valueOf(divisor.getText());
            kernel[4] = Float.valueOf(middleMiddle.getText()) / Float.valueOf(divisor.getText());
            kernel[5] = Float.valueOf(middleRight.getText()) / Float.valueOf(divisor.getText());
            kernel[6] = Float.valueOf(bottomLeft.getText()) / Float.valueOf(divisor.getText());
            kernel[7] = Float.valueOf(bottomMiddle.getText()) / Float.valueOf(divisor.getText());
            kernel[8] = Float.valueOf(bottomRight.getText()) / Float.valueOf(divisor.getText());

            BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, kernel));
            for (int i = 0; i < numKernelApplications; i++) {
                sourceImage = filteredImage;
                filteredImage = op.filter(sourceImage, null);
                newImage = SwingFXUtils.toFXImage(filteredImage, null);
                System.out.println("working");
            }
            primaryController.getImageView().setImage(newImage);
        } catch (NumberFormatException e1) {
            showBlankFieldAlert();
            LOGGER.info("User attempted to filter image with a blank field");
        } catch (ImagingOpException e2) {
            showInvalidDivisorAlert();
            LOGGER.info("User attempted to use an invalid divisor: " + divisor.getText());
        }
    }

    @FXML
    private void setNumKernelApplications(MouseEvent e) {
        numKernelApplications = (int) kernelApplications.getValue();
    }

    public void setPrimaryController(PrimaryController controller) {
        this.primaryController = controller;
    }

    private void showBlankFieldAlert() {
        Alert blankFieldAlert = new Alert(Alert.AlertType.ERROR, "Error: One or more " +
                " of the fields has been left blank! Enter a value in the blank fields and try " +
                "again");
        blankFieldAlert.setTitle("Error Dialog");
        blankFieldAlert.setHeaderText("Field/s Left Blank");
        blankFieldAlert.showAndWait();
    }

    private void showInvalidDivisorAlert() {
        Alert invalidDivisorAlert = new Alert(Alert.AlertType.ERROR, "Error: Invalid " +
                "divisor entered");
        invalidDivisorAlert.setTitle("Error Dialog");
        invalidDivisorAlert.setHeaderText("Invalid Divisor");
        invalidDivisorAlert.showAndWait();
    }
}
