/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson.geometry;

import fr.cnes.geojson.object.Geometry;
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
public class GeometryCollectionTest {
    
    public GeometryCollectionTest() {
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
     * Test of addGeometry method, of class GeometryCollection.
     */
    @Test
    public void testAddGeometry() {
        System.out.println("addGeometry");
        Point point = new Point();
        point.setPoint(new double[]{100.0, 0.0});  
        LineString lineString = new LineString(new double[][]{{101.0, 0.0}, {102.0, 1.0}});
        GeometryCollection instance = new GeometryCollection();
        instance.addGeometry(point);
        instance.addGeometry(lineString);
        assertNotNull(instance.getCoordinates());
    }

    /**
     * Test of getCoordinates method, of class GeometryCollection.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        Point point = new Point();
        point.setPoint(new double[]{100.0, 0.0});  
        LineString lineString = new LineString(new double[][]{{101.0, 0.0}, {102.0, 1.0}});
        GeometryCollection instance = new GeometryCollection();
        instance.addGeometry(point);
        instance.addGeometry(lineString);
        assertEquals(instance.getCoordinates().size(),2);
    }

    /**
     * Test of equals method, of class GeometryCollection.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new GeometryCollection();
        ((GeometryCollection)obj).addGeometry(new Point(new double[]{10,11}));
        ((GeometryCollection)obj).addGeometry(new Point(new double[]{10,12}));
        GeometryCollection instance = new GeometryCollection();
        instance.addGeometry(new Point(new double[]{10,11}));
        instance.addGeometry(new Point(new double[]{10,12}));
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of length method, of class GeometryCollection.
     */
    @Test
    public void testLength() {
        System.out.println("length");
        GeometryCollection instance = new GeometryCollection();
        int expResult = 0;
        int result = instance.length();
        assertEquals(expResult, result);

    }

    
}
