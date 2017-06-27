/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson.geometry;

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
public class LineStringTest {
    
    public LineStringTest() {
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
     * Test of setPoints method, of class LineString.
     */
    @Test
    public void testSetPoints_doubleArrArr() {
        System.out.println("setPoints");
        double[][] points = new double[][]{{100.0, 0.0}, {101.0, 1.0}};
        LineString instance = new LineString();
        instance.setPoints(points);
        assertNotNull(instance.getCoordinates());
    }

    /**
     * Test of setPoints method, of class LineString.
     */
    @Test
    public void testSetPoints_PositionArr() {
        System.out.println("setPoints");
        Position[] points = new Position[]{
            new Position(new double[]{100.0, 0.0}),
            new Position(new double[]{101.0, 1.0})
        };
        LineString instance = new LineString();
        instance.setPoints(points);
        assertNotNull(instance.getCoordinates());
    }

    /**
     * Test of getPoints method, of class LineString.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        LineString instance = new LineString();
        double[][] points = new double[][]{{100.0, 0.0}, {101.0, 1.0}};
        instance.setPoints(points);        
        Object expResult = points;
        Object result = instance.getPoints(double[][].class);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCoordinates method, of class LineString.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        LineString instance = new LineString();
        double[][] points = new double[][]{{100.0, 0.0}, {101.0, 1.0}};
        instance.setPoints(points);        
        Object expResult = points;
        Object result = instance.getCoordinates();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class LineString.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new LineString(new double[][]{
            {10,10},{11,11}
        });
        LineString instance = new LineString();
        instance.setPoints(new double[][]{
            {10,10},{11,11}
        });
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of not equals method, of class LineString.
     */
    @Test
    public void testNotEquals() {
        System.out.println("equals");
        Object obj = new LineString(new double[][]{
            {10,10},{11,11.5}
        });
        LineString instance = new LineString();
        instance.setPoints(new double[][]{
            {10,10},{11,11}
        });
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }    

    
}
