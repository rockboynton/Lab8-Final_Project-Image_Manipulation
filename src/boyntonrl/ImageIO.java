/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 8 Final Project - Image Manipulation
 * Name: Rock Boynton
 * Created: 2/1/18
 */

package boyntonrl;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;

/**
 * This class is responsible for loading and saving images
 */
public class ImageIO {

    /**
     * Radix for hexadecimal numbers
     */
    public static final int HEX = 16;
    /**
     * String specifying the first line of an MSOE file.
     */
    public static final String MSOE_FILE_HEADER = "MSOE";
    /**
     * Integer value of a color or alpha
     */
    public static final int COLOR_RANGE = 255;

    /**
     * Reads in the specified image file and returns a javafx.scene.image.Image object containing
     * the image.
     * @param file the specified image file
     * @return
     */
    public static Image read(File file) throws IndexOutOfBoundsException, IOException {
        Image image;
        String path = file.getPath();
        String extension = path.substring(path.lastIndexOf("."));
        if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".gif")) { // Java supported image format (.jpg,.png,.gif)
            image = new Image(file.toURI().toString());
        } else if (extension.equals(".msoe")) {
            image = readMSOE(file);
        } else {
            throw new IOException();
        }
        return image;
    }

    /**
     * Writes the specified image to the specified file.
     * @param image image to be saved
     * @param file new image file to be created
     * @throws IOException
     */
    public static void write(Image image, File file) throws IOException {
        // TODO
        String path = file.getPath();
        String extension = path.substring(path.lastIndexOf("."));
        if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".gif")) { // Java supported image format (.jpg,.png,.gif)
            javax.imageio.ImageIO.write(SwingFXUtils.fromFXImage(image, null), extension
                    .substring(1), file);
        } else if (extension.equals(".msoe")) {
            writeMSOE(image, file);
        } else {
            throw new IOException();
        }
    }

    private static Image readMSOE(File file) throws IndexOutOfBoundsException, IOException {
        // TODO
        WritableImage image;
        PixelWriter writer;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String header = br.readLine();
            String[] dimensions = br.readLine().split("\\s+");
            String[] pixels;
            int width;
            int height;
            Color color;

            if (!header.equals("MSOE")) {
                throw new IOException(); // msoe file not formatted properly
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

//            String line = br.readLine();
//            int lineNumber = 0;
//            while (line != null) {
//                pixels = line.split("\\s+");
//                for (int i = 0; i < width; i++) {
//                    color = stringToColor(pixels[i]);
//                    writer.setColor(i, lineNumber, color);
//                }
//                line = br.readLine();
//                lineNumber++;
//            }
        } catch (IOException ioe) {
            throw ioe;
        } catch (IndexOutOfBoundsException ioob) {
            throw ioob;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static Image readBMSOE(File file) throws IOException{
//    }

    private static void writeBMSOE(Image image, File file) {
        // TODO
    }

    private static Color stringToColor(String colorStr) throws InputMismatchException {
        // checks valid length, # indicating hex, and valid hex color string
        if (colorStr.length() != 9 || colorStr.charAt(0) != '#' || !colorStr.substring(1).matches(
                "[0-9A-Fa-f]+")) {
            throw new InputMismatchException("invalid hex color: " + colorStr);
        }
        return new Color(
                (double) Integer.valueOf(colorStr.substring(1, 3), HEX) / COLOR_RANGE,
                (double) Integer.valueOf(colorStr.substring(3, 5), HEX) / COLOR_RANGE,
                (double) Integer.valueOf(colorStr.substring(5, 7), HEX) / COLOR_RANGE,
                (double) Integer.valueOf(colorStr.substring(7, 9), HEX) / COLOR_RANGE);

    }

    private static String colorToString(Color color) {

        int red = (int) (color.getRed() * COLOR_RANGE);
        int green = (int) (color.getGreen() * COLOR_RANGE);
        int blue = (int) (color.getBlue() * COLOR_RANGE);
        int alpha = (int) (color.getOpacity() * COLOR_RANGE);

        return String.format("#%02X%02X%02X%02X", red, green, blue, alpha);
    }

}
