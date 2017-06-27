 /******************************************************************************
 * Copyright 2017 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
 *
 * This file is part of Regards.
 *
 * Regards is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Regards is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Regards.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package fr.cnes.geojson;

/**
 * Utility class.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class Utils {

    /**
     * Double precision.
     */
    public final static double EPSILON = 0.0000001;

    /**
     * Tests the equality of two doubles.
     * @param a First double to test
     * @param b Second double to test
     * @param eps double precision
     * @return True when the two doubles are equals otherwise False
     */
    public static boolean equals(double a, double b, double eps) {
        if (a == b) {
            return true;
        }
        return Math.abs(a - b) < eps;
    }

    /**
     * Test the equality of two double with EPSILON precision.
     * @param a First double to compare
     * @param b Second double to compare
     * @return True when the two doubles are equals otherwise False
     */
    public static boolean equals(double a, double b) {
        return equals(a, b, EPSILON);
    }
    
    /**
     * Tests whether the object is not null.
     * @param obj object to test
     * @throws IllegalArgumentException Will throw an exception when the object 
     * is null
     */
    public static void checkNotNull(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("the given argument in the constructor cannot be null");
        }
    } 
    
    /**
     * Reverses the coordinates of the array.
     * This algorithm is used to reverse indices in the array to clockwise/counterclockwise
     * a polygon
     * @param array An array of coordinates (longitude, latitude [,altitude])
     */
    public static void reverse(final double[][] array) {
        if (array == null) { 
            return;
        }
        int i = 0;
        int j = array.length - 1;
        double[] tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }    
    
    /**
     * Test whether the polygon is clockwise.
     * @param vertices vertices     
     * @return True when the polygon is clockwise other False
     */
    public static boolean isClockwisedPolygon(final double vertices[][]) {
        double sum = 0.0;
        for (int i = 0; i < vertices.length; i++) {
            double[] v1 = vertices[i];
            double[] v2 = vertices[(i + 1) % vertices.length];
            sum += (v2[0] - v1[0]) * (v2[1] + v1[1]);
        }
        return sum > 0.0;
    }

    /**
     * Tests whether the polygon is counter  clockwise.
     * @param vertices vertices     
     * @return True when the polygon is counterclockwise other False
     */    
    public static boolean isCounterClockwisedPolygon(final double vertices[][]) {
        return !isClockwisedPolygon(vertices);
    }
    

}
