/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson.geometry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class MultiPointTest {
    
    public MultiPointTest() {
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
     * Test of setPoints method, of class MultiPoint.
     */
    @Test
    public void testSetPoints_doubleArrArr() {
        System.out.println("setPoints");
        double[][] points = new double[][]{
            {100.0, 0.0}, {101.0, 1.0}
        };
        MultiPoint instance = new MultiPoint();
        instance.setPoints(points);
        assertNotNull(instance.getPoints(double[][].class));
    }

    /**
     * Test of setPoints method, of class MultiPoint.
     */
    @Test
    public void testSetPoints_PositionArr() {
        System.out.println("setPoints");
        Position[] points = new Position[]{
            new Position(new double[]{100.0, 0.0}),
            new Position(new double[]{101.0, 1.0}),
        };
        MultiPoint instance = new MultiPoint();
        instance.setPoints(points);
        assertArrayEquals(instance.getPoints(Position[].class), points);
    }

    /**
     * Test of getPoints method, of class MultiPoint.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        MultiPoint instance = new MultiPoint();
        double[][] points = new double[][]{
            {100.0, 0.0}, {101.0, 1.0}
        }; 
        Object expResult = points;
        instance.setPoints(points);
        Object result = instance.getPoints(double[][].class);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCoordinates method, of class MultiPoint.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        MultiPoint instance = new MultiPoint();
        double[][] points = new double[][]{
            {100.0, 0.0}, {101.0, 1.0}
        }; 
        Object expResult = points;
        instance.setPoints(points);
        Object result = instance.getCoordinates();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class MultiPoint.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new MultiPoint(new double[][]{{10,11},{10,10}});
        MultiPoint instance = new MultiPoint();
        instance.setPoints(new double[][]{{10,11},{10,10}});
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class MultiPoint.
     */
    @Test
    public void testNotEquals() {
        System.out.println("equals");
        Object obj = new MultiPoint(new double[][]{{10,11},{10,10.5}});
        MultiPoint instance = new MultiPoint();
        instance.setPoints(new double[][]{{10,11},{10,10}});
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }    

    /**
     * Test of computeBbox method, of class MultiPoint.
     */
    @Test
    public void testComputeBbox() {
        System.out.println("computeBbox");
        MultiPoint instance = new MultiPoint(new double[][]{
            {10,10},{11,11}
        });
        instance.computeBbox();
        double[] bbox= instance.getBbox();
        double[] expected = new double[]{10,10,11,11};
        assertArrayEquals(expected, bbox,0.0);
    }
    
}
