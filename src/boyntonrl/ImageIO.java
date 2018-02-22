/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 8 Final Project - Image Manipulation
 * Name: Rock Boynton
 * Created: 2/1/18
 */

package boyntonrl;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for loading and saving images
 */
public class ImageIO {

    private static final Logger LOGGER = Lab08.LOGGER;

    /**
     * Radix for hexadecimal numbers
     */
    public static final int HEX = 16;
    /**
     * String specifying the first line of an MSOE file.
     */
    public static final String MSOE_FILE_HEADER = "MSOE";
    /**
     * char sequence specifying the first four bytes of a BMSOE file.
     */
    public static final char[] BMSOE_FILE_HEADER = {'B', 'M', 'S', 'O', 'E'};
    /**
     * Integer value of a color or alpha
     */
    public static final double COLOR_RANGE = 255.0;

    /**
     * Reads in the specified image file and returns a javafx.scene.image.Image object containing
     * the image.
     * @param file the specified image file
     * @return image from the specified file
     */
    public static Image read(File file) {
        Image image = null;
        String path = file.getPath();
        String extension = path.substring(path.lastIndexOf("."));
        try {
            // Java supported image format (.jpg,.png,.gif)
            if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".gif")) {
                image = new Image(file.toURI().toString());
            } else if (extension.equals(".msoe")) {
                image = readMSOE(file);
            } else if (extension.equals(".bmsoe")) {
                image = readBMSOE(file);
            } else {
                throw new IOException();
            }
            LOGGER.info("user opened image");
        } catch (IndexOutOfBoundsException e) {
            showReadFailureAlert();
        } catch (IOException e) {
            showReadFailureAlert();
        }
        return image;
    }

    /**
     * Writes the specified image to the specified file.
     * @param image image to be saved
     * @param file new image file to be created
     */
    public static void write(Image image, File file) {
        String path = file.getPath();
//        String name = path.substring(0, path.lastIndexOf("."));
//        String extension = name.substring(name.lastIndexOf("."));
        String extension = path.substring(path.lastIndexOf("."));
        try {
            // Java supported image format (.jpg,.png,.gif)
            if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".gif")) {
                javax.imageio.ImageIO.write(SwingFXUtils.fromFXImage(image, null), extension
                        .substring(1), file);
            } else if (extension.equals(".msoe")) {
                writeMSOE(image, file);
            } else if (extension.equals(".bmsoe")) {
                writeBMSOE(image, file);
            } else {
                throw new IOException();
            }
            showSaveSuccessfulAlert();
        } catch (IOException e) {
            showUnsupportedFileTypeAlert(extension);
        }
    }

    private static Image readMSOE(File file) {
        WritableImage image = null;
        PixelWriter writer;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String header = br.readLine();
            String[] dimensions = br.readLine().split("\\s+");
            String[] pixels;
            int width;
            int height;
            Color color;

            if (!header.equals("MSOE")) {
                throw new InputMismatchException(); // msoe file not formatted properly
            }
            width = Integer.parseInt(dimensions[0]);
            height = Integer.parseInt(dimensions[1]);
            image = new WritableImage(width, height);
            writer = image.getPixelWriter();
            for (int row = 0; row < height; row++) {
                pixels = br.readLine().split("\\s+");
                for (int column = 0; column < width; column++) {
                    color = stringToColor(pixels[column]);
                    writer.setColor(column, row, color);
                }
            }
        } catch (IOException ioe) {
            showReadFailureAlert();
            LOGGER.log(Level.WARNING, "Could not open .bmsoe file", ioe);
        } catch (InputMismatchException ime) {
            showReadFailureAlert();
            LOGGER.log(Level.WARNING, "Could not open .bmsoe file", ime);
        } catch (IndexOutOfBoundsException iob) {
            showReadFailureAlert();
            image = null;
            LOGGER.log(Level.WARNING, "Could not open .bmsoe file", iob);
        } catch (NullPointerException npe) {
            showReadFailureAlert();
            image = null;
            LOGGER.log(Level.WARNING, "Could not open .bmsoe file", npe);
        }
        return image;
    }

    private static void writeMSOE(Image image, File file) {
        int width;
        int height;
        String pixel;

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            width = (int) image.getWidth();
            height = (int) image.getHeight();
            writer.println(MSOE_FILE_HEADER);
            writer.println(width + " " + height);
            PixelReader reader = image.getPixelReader();

            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    pixel = colorToString(reader.getColor(column, row));
                    writer.print(pixel + " ");
                }
                writer.println();
            }
        } catch (IOException ioe) {
            showSaveFailureAlert();
            LOGGER.log(Level.WARNING, "Could not write .msoe file", ioe);
        }
    }

    private static Image readBMSOE(File file) {
        WritableImage image = null;
        PixelWriter writer;

        try (DataInputStream data = new DataInputStream(new FileInputStream(file))) {
            char[] header = new char[BMSOE_FILE_HEADER.length];
            int width;
            int height;
            Color color;
            double r;
            double g;
            double b;
            double a;

            for (int i = 0; i < BMSOE_FILE_HEADER.length; i++) {
                header[i] = (char) data.readUnsignedByte();
            }
            if (!Arrays.equals(header, BMSOE_FILE_HEADER)) {
                throw new InputMismatchException(); // bmsoe file not formatted properly
            }
            width = data.readInt();
            height = data.readInt();
            Color[] pixelsInRow = new Color[width];
            image = new WritableImage(width, height);
            writer = image.getPixelWriter();
            for (int row = 0; row < height; row++) {
                for (int i = 0; i < width; i++) {
                    r = data.readUnsignedByte() / COLOR_RANGE;
                    g = data.readUnsignedByte() / COLOR_RANGE;
                    b = data.readUnsignedByte() / COLOR_RANGE;
                    a = data.readUnsignedByte() / COLOR_RANGE;
                    pixelsInRow[i] = new Color(r, g, b, a);
                }
                for (int column = 0; column < width; column++) {
                    color = pixelsInRow[column];
                    writer.setColor(column, row, color);
                }
            }
        } catch (IOException ioe) {
            showReadFailureAlert();
            LOGGER.log(Level.WARNING, "Could not open .bmsoe file", ioe);
        } catch (InputMismatchException ime) {
            showReadFailureAlert();
            LOGGER.log(Level.WARNING, "Could not open .bmsoe file", ime);
        } catch (IndexOutOfBoundsException iob) {
            showReadFailureAlert();
            image = null;
            LOGGER.log(Level.WARNING, "Could not open .bmsoe file", iob);
        } catch (NullPointerException npe) {
            showReadFailureAlert();
            image = null;
            LOGGER.log(Level.WARNING, "Could not open .bmsoe file", npe);
        } catch (NegativeArraySizeException nase) {
            showReadFailureAlert();
            image = null;
            LOGGER.log(Level.WARNING, "Could not open .bmsoe file", nase);
        }
        return image;
    }

    private static void writeBMSOE(Image image, File file) {
        int width;
        int height;
        Color pixel;

        try (DataOutputStream data = new DataOutputStream(new FileOutputStream(file))) {
            width = (int) image.getWidth();
            height = (int) image.getHeight();
            for (int i = 0; i < BMSOE_FILE_HEADER.length; i++) {
                data.write(BMSOE_FILE_HEADER[i]);
            }
            data.writeInt(width);
            data.writeInt(height);
            PixelReader reader = image.getPixelReader();
            for (int row = 0; row < height; row++) {
                for (int column = 0; column < width; column++) {
                    pixel = reader.getColor(column, row);
                    data.write((int) (pixel.getRed() * COLOR_RANGE));
                    data.write((int) (pixel.getGreen() * COLOR_RANGE));
                    data.write((int) (pixel.getBlue() * COLOR_RANGE));
                    data.write((int) (pixel.getOpacity() * COLOR_RANGE));
                }
            }
        } catch (IOException ioe) {
            showSaveFailureAlert();
            LOGGER.log(Level.WARNING, "Could not write .bmsoe file", ioe);
        }
    }

    private static Color stringToColor(String colorStr) throws InputMismatchException {
        // checks valid length, # indicating hex, and valid hex color string
        if (colorStr.length() != 9 || colorStr.charAt(0) != '#' || !colorStr.substring(1).matches(
                "[0-9A-Fa-f]+")) {
            throw new InputMismatchException("invalid hex color: " + colorStr);
        }
        return new Color(
                // extracting four 2-digit hex values from a string
                Integer.valueOf(colorStr.substring(1, 3), HEX) / COLOR_RANGE,
                Integer.valueOf(colorStr.substring(3, 5), HEX) / COLOR_RANGE,
                Integer.valueOf(colorStr.substring(5, 7), HEX) / COLOR_RANGE,
                Integer.valueOf(colorStr.substring(7, 9), HEX) / COLOR_RANGE);
    }

    private static String colorToString(Color color) {

        int red = (int) (color.getRed() * COLOR_RANGE);
        int green = (int) (color.getGreen() * COLOR_RANGE);
        int blue = (int) (color.getBlue() * COLOR_RANGE);
        int alpha = (int) (color.getOpacity() * COLOR_RANGE);

        return String.format("#%02X%02X%02X%02X", red, green, blue, alpha);
    }

    private static void showSaveFailureAlert() {
        Alert saveFailureAlert = new Alert(Alert.AlertType.ERROR, "Error: Could not " +
                "save image to specified file. ");
        saveFailureAlert.setTitle("Error Dialog");
        saveFailureAlert.setHeaderText("Save Failure");
        saveFailureAlert.showAndWait();
    }

    private static void showReadFailureAlert() {
        Alert readFailureAlert = new Alert(Alert.AlertType.ERROR, "Error: Could not " +
                "read image from specified file. File may be corrupt ");
        readFailureAlert.setTitle("Error Dialog");
        readFailureAlert.setHeaderText("Read Failure");
        readFailureAlert.showAndWait();
    }

    private static void showUnsupportedFileTypeAlert(String extension) {
        Alert unsupportedFileTypeAlert = new Alert(Alert.AlertType.ERROR, "Error: " +
                "the file entered could not be saved");
        unsupportedFileTypeAlert.setTitle("Error Dialog");
        unsupportedFileTypeAlert.setHeaderText("Invalid file type " + extension);
        unsupportedFileTypeAlert.showAndWait();
    }

    private static void showSaveSuccessfulAlert() {
        Alert saveSuccessfulAlert = new Alert(Alert.AlertType.INFORMATION, "Your " +
                "image saved successfully!");
        saveSuccessfulAlert.setTitle("Message Dialog");
        saveSuccessfulAlert.setHeaderText("Save Success");
        saveSuccessfulAlert.showAndWait();
    }
}
