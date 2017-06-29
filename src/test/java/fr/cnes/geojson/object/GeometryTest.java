/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson.object;

import fr.cnes.geojson.geometry.Point;
import java.util.HashMap;
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
public class GeometryTest {
    
    public GeometryTest() {
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
     * Test of createGeometry method, of class Geometry.
     */
    @Test
    public void testCreateGeometry() {
        System.out.println("createGeometry");
        Class clazz = null;
        Geometry expResult = new Point(new HashMap<>());
        Geometry<Point> result = Geometry.createGeometry(Point.class, new HashMap<>());
        assertEquals(expResult, result.getShape());
    }

    
}
