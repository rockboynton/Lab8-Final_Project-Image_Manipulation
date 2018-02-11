/*
 * SE1021 - 021
 * Winter 2017
 * Lab: Lab 8 Final Project - Image Manipulation
 * Name: Rock Boynton
 * Created: 2/1/18
 */

package boyntonrl;

import javafx.scene.paint.Color;

/**
 * Functional interface in which the method, named transform, accepts three arguments: the x
 * and y location of the pixel and its color. The method must return the color for the pixel
 * after the applying the transformation.
 */
@FunctionalInterface
interface Transformable {
    Color transform(int x, int y, Color color);
}
