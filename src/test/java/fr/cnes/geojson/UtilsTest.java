/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class UtilsTest {
    
    public UtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of equals method, of class Utils.
     */
    @Test
    public void testEquals_3args() {
        System.out.println("equals");
        double a = 10.0;
        double b = 10.00000001;
        double eps = 0.0000001;
        boolean expResult = true;
        boolean result = Utils.equals(a, b, eps);
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Utils.
     */
    @Test
    public void testEquals_double_double() {
        System.out.println("equals");
        double a = 10.0;
        double b = 10.000000001;
        boolean expResult = true;
        boolean result = Utils.equals(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test of reverse method, of class Utils.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        double[][] result = new double[][] {
            {0,4},{0,3},{0,2},{0,1},{0,0}
        };        
        double[][] array = new double[][] {
            {0,0},{0,1},{0,2},{0,3},{0,4}
        };
        Utils.reverse(array);
        Arrays.deepEquals(result, array);
    }

    /**
     * Test of isClockwisedPolygon method, of class Utils.
     */
    @Test
    public void testIsClockwisedPolygon() {
        System.out.println("isClockwisedPolygon");
        double[][] vertices = new double[][]{
            {-180,90},{180,90},{180,-90},{-180,-90},{-180,90}
        };
        boolean expResult = true;
        boolean result = Utils.isClockwisedPolygon(vertices);
        assertEquals(expResult, result);
    }

    /**
     * Test of isCounterClockwisedPolygon method, of class Utils.
     */
    @Test
    public void testIsCounterClockwisedPolygon() {
        System.out.println("isCounterClockwisedPolygon");
        double[][] vertices = new double[][]{
            {-180,90},{180,90},{180,-90},{-180,-90},{-180,90}
        };
        boolean expResult = false;
        boolean result = Utils.isCounterClockwisedPolygon(vertices);
        assertEquals(expResult, result);
    }
    
}
