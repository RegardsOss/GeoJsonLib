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
public class PointTest {
    
    public PointTest() {
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
     * Test of setPoint method, of class Point.
     */
    @Test
    public void testSetPoint_doubleArr() {
        System.out.println("setPoint");
        double[] position = new double[]{100.0, 0.0};
        Point instance = new Point();
        instance.setPoint(position);
        assertNotNull(instance.getPoints(double[].class));
    }

    /**
     * Test of setPoint method, of class Point.
     */
    @Test
    public void testSetPoint_Position() {
        System.out.println("setPoint");
        Position position = new Position(100,0);
        Point instance = new Point();
        instance.setPoint(position);
        assertNotNull(instance.getPoints(Position.class));
    }

    /**
     * Test of getPoints method, of class Point.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        double[] position = new double[]{100.0, 0.0};
        Point instance = new Point();
        instance.setPoint(position);
        Object expResult = position;
        Object result = instance.getPoints(double[].class);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCoordinates method, of class Point.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        double[] position = new double[]{100.0, 0.0};
        Point instance = new Point();
        instance.setPoint(position);
        double[] expResult = position;
        double[] result = instance.getCoordinates();
        assertEquals(expResult, result);
    }


    /**
     * Test of equals method, of class Point.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Point();
        ((Point)obj).setPoint(new double[]{10.0,11.0});
        Point instance = new Point();
        instance.setPoint(new double[]{10.0,11.0});
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of not equals method, of class Point.
     */
    @Test
    public void testNotEquals() {
        System.out.println("not equals");
        Object obj = new Point();
        ((Point)obj).setPoint(new double[]{10.0,11.5});
        Point instance = new Point();
        instance.setPoint(new double[]{10.0,11.0});
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    
}
