/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class PolygonTest {

    public PolygonTest() {
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

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of equals method, of class Polygon.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Polygon(new double[][][]{
            {{10, 10}, {11, 10}, {11, 11}, {10, 11}, {10, 10}}
        });
        Polygon instance = new Polygon();
        instance.setPoints(new double[][][]{
            {{10, 10}, {11, 10}, {11, 11}, {10, 11}, {10, 10}}
        });
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of not equals method, of class Polygon.
     */
    @Test
    public void testNotEquals() {
        System.out.println("equals");
        Object obj = new Polygon(new double[][][]{
            {{10, 10}, {11, 10.1}, {11, 11}, {10, 11}, {10, 10}}
        });
        Polygon instance = new Polygon();
        instance.setPoints(new double[][][]{
            {{10, 10}, {11, 10}, {11, 11}, {10, 11}, {10, 10}}
        });
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of setPoints method, of class Polygon.
     */
    @Test
    public void testSetPoints_3args() {
        System.out.println("setPoints");
        LineString lineString1 = new LineString(new double[][]{
            {10, 10}, {15, 10}
        });
        LineString lineString2 = new LineString(new double[][]{
            {15, 10}, {15, 15}
        });        
        LineString lineString3 = new LineString(new double[][]{
            {15, 15}, {10, 15}
        });         
        LineString lineString4 = new LineString(new double[][]{
            {10, 15}, {10, 10}
        });          
        LineString[] exteriorRing = new LineString[] {
            lineString1,lineString2,lineString3,lineString4
        };
        List interiorRings = new ArrayList();
        boolean fixOrder = false;
        Polygon instance = new Polygon();
        instance.setPoints(exteriorRing, interiorRings, fixOrder);
    }

}
