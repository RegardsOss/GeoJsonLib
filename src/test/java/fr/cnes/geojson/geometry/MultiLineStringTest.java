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
public class MultiLineStringTest {
    
    public MultiLineStringTest() {
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
     * Test of setPoints method, of class MultiLineString.
     */
    @Test
    public void testSetPoints_doubleArrArrArr() {
        System.out.println("setPoints");
        double[][][] points = new double[][][]{
            { {100.0, 0.0}, {101.0, 1.0} },
            { {102.0, 2.0}, {103.0, 3.0} }
        };
        MultiLineString instance = new MultiLineString();
        instance.setPoints(points);
        assertNotNull(instance.getCoordinates());
    }

    /**
     * Test of setPoints method, of class MultiLineString.
     */
    @Test
    public void testSetPoints_LineStringArr() {
        System.out.println("setPoints");
        LineString[] lineStrings = new LineString[] {
            new LineString(new double[][]{{100.0, 0.0}, {101.0, 1.0}}),
            new LineString(new double[][]{{102.0, 2.0}, {103.0, 3.0}})
        };
        MultiLineString instance = new MultiLineString();
        instance.setPoints(lineStrings);
        assertNotNull(instance.getPoints(double[][][].class));
    }

    /**
     * Test of getPoints method, of class MultiLineString.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        MultiLineString instance = new MultiLineString();
        double[][][] points = new double[][][]{
            { {100.0, 0.0}, {101.0, 1.0} },
            { {102.0, 2.0}, {103.0, 3.0} }
        };        
        instance.setPoints(points);
        Object expResult = points;
        Object result = instance.getPoints(double[][][].class);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCoordinates method, of class MultiLineString.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        MultiLineString instance = new MultiLineString();
        double[][][] points = new double[][][]{
            { {100.0, 0.0}, {101.0, 1.0} },
            { {102.0, 2.0}, {103.0, 3.0} }
        };        
        instance.setPoints(points);        
        double[][][] expResult = points;
        double[][][] result = instance.getCoordinates();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of equals method, of class MultiLineString.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new MultiLineString(new double[][][]{
            {{10,10},{10,11}},
            {{11,10},{11,11}}
        });
        MultiLineString instance = new MultiLineString();
        instance.setPoints(new double[][][]{
            {{10,10},{10,11}},
            {{11,10},{11,11}}
        });
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of not equals method, of class MultiLineString.
     */
    @Test
    public void testNotEquals() {
        System.out.println("equals");
        Object obj = new MultiLineString(new double[][][]{
            {{10,10},{10,11.5}},
            {{11,10},{11,11}}
        });
        MultiLineString instance = new MultiLineString();
        instance.setPoints(new double[][][]{
            {{10,10},{10,11}},
            {{11,10},{11,11}}
        });
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }    
}
