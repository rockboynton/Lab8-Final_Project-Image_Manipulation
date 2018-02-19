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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.image.Kernel;
import java.io.BufferedReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Arrays.*;

import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;

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

    private List<TextField> kernelFields = asList(
            topLeft, topMiddle, topRight,
            middleLeft, middleMiddle, middleRight,
            bottomLeft, bottomMiddle, bottomRight);

    private double defaultDivisor = 9;

    private DecimalFormat format = new DecimalFormat("0.#");

    private double[] defaultBlur = {
            0, 1, 0,
            1, 5, 1,
            0, 1, 0};

    @FXML
    public void blur(ActionEvent e) {
        topLeft     .setText(format.format(defaultBlur[0]));
        topMiddle   .setText(format.format(defaultBlur[1]));
        topRight    .setText(format.format(defaultBlur[2]));
        middleLeft  .setText(format.format(defaultBlur[3]));
        middleMiddle.setText(format.format(defaultBlur[4]));
        middleRight .setText(format.format(defaultBlur[5]));
        bottomLeft  .setText(format.format(defaultBlur[6]));
        bottomMiddle.setText(format.format(defaultBlur[7]));
        bottomRight .setText(format.format(defaultBlur[8]));
    }

    @FXML
    public void sharpen(ActionEvent e) {

    }

    @FXML
    public void edge(ActionEvent e) {

    }

    @FXML
    public void apply(ActionEvent e) {
        float[] kernel = new float[9];
        kernel[0] = Float.valueOf(topLeft.getText())      / Float.valueOf(divisor.getText());
        kernel[1] = Float.valueOf(topMiddle.getText())    / Float.valueOf(divisor.getText());
        kernel[2] = Float.valueOf(topRight.getText())     / Float.valueOf(divisor.getText());
        kernel[3] = Float.valueOf(middleLeft.getText())   / Float.valueOf(divisor.getText());
        kernel[4] = Float.valueOf(middleMiddle.getText()) / Float.valueOf(divisor.getText());
        kernel[5] = Float.valueOf(middleRight.getText())  / Float.valueOf(divisor.getText());
        kernel[6] = Float.valueOf(bottomLeft.getText())   / Float.valueOf(divisor.getText());
        kernel[7] = Float.valueOf(bottomMiddle.getText()) / Float.valueOf(divisor.getText());
        kernel[8] = Float.valueOf(bottomRight.getText())  / Float.valueOf(divisor.getText());

        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, kernel));



    }

    public void setPrimaryController(PrimaryController controller) {
        this.primaryController = controller;
    }
}
