package boyntonrl;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.InputMismatchException;

/**
 * This class is responsible for loading and saving images
 */
public class ImageIO {

    public static final int HEX = 16;

    private static Image currentImage;

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
        currentImage = image;
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
        System.out.println(path);
        String extension = path.substring(path.lastIndexOf("."));
        System.out.println(extension);
        if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".gif")) { // Java supported image format (.jpg,.png,.gif)
            javax.imageio.ImageIO.write(SwingFXUtils.fromFXImage(image, null), extension
                    .substring(1), file);
            System.out.println("done");
        } else if (extension.equals(".msoe")) {
            writeMSOE(image, file);
        } else {
            throw new IOException();
        }
        currentImage = image;
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
            System.out.println(width);
            height = Integer.parseInt(dimensions[1]);
            image = new WritableImage(width, height);
            writer = image.getPixelWriter();
//            for (int row = 0; row < height; row++) {
//                pixels = br.readLine().split("\\s+");
////                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//                for (int column = 0; column < width; column++) {
////                    System.out.println(row + " " + column);
//                    color = stringToColor(pixels[column]);
////                    System.out.println(color);
//                    writer.setColor(column, row, color);
//                }
//            }
            String line = br.readLine();
            int lineNumber = 0;
            while (line != null) {
                pixels = line.split("\\s+");
                for (int i = 0; i < width; i++) {
                    color = stringToColor(pixels[i]);
                    writer.setColor(i, lineNumber, color);
                }
                line = br.readLine();
                lineNumber++;
            }
        }
        return image;
    }

    private static void writeMSOE(Image image, File file) {
        // TODO
    }

//    private static Image readBMSOE(File file) throws IOException{
//        // TODO
//    }

    private static void writeBMSOE(Image image, File file) {
        // TODO
    }

    private static Color stringToColor(String colorStr) throws InputMismatchException {
        // checks valid length, # indicating hex, and valid hex color string
        System.out.println(colorStr);
        if (colorStr.length() != 9 || colorStr.charAt(0) != '#' || !colorStr.substring(1).matches
                ("[0-9A-F]+")) {
            throw new InputMismatchException("invalid hex color");
        }
        return new Color(
                (double) Integer.valueOf(colorStr.substring(1, 3), HEX) / 255,
                (double) Integer.valueOf(colorStr.substring(3, 5), HEX) / 255,
                (double) Integer.valueOf(colorStr.substring(5, 7), HEX) / 255,
                (double) Integer.valueOf(colorStr.substring(7, 9), HEX) / 255) ;

    }

}
