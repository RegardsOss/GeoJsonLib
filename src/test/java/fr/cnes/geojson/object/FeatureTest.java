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
public class FeatureTest {
    
    public FeatureTest() {
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
     * Test of getGeometry method, of class Feature.
     */
    @Test
    public void testGetGeometry() {
        System.out.println("getGeometry");
        Feature instance = new Feature();
        Geometry expResult = null;
        Geometry result = instance.getGeometry();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGeometry method, of class Feature.
     */
    @Test
    public void testSetGeometry() {
        System.out.println("setGeometry");
        Geometry geometry = new Point();
        geometry.setCoordinates(new double[]{0,12});
        Feature instance = new Feature();
        instance.setGeometry(geometry);
    }

    /**
     * Test of getProperties method, of class Feature.
     */
    @Test
    public void testGetProperties() {
        System.out.println("getProperties");
        Feature instance = new Feature();
        HashMap<String, Object> expResult = new HashMap<>();
        HashMap<String, Object> result = instance.getProperties();
        assertEquals(expResult, result);
    }

    /**
     * Test of setProperties method, of class Feature.
     */
    @Test
    public void testSetProperties() {
        System.out.println("setProperties");
        HashMap<String, Object> properties = new HashMap<>();
        Feature instance = new Feature();
        instance.setProperties(properties);
    }

    /**
     * Test of getId method, of class Feature.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Feature instance = new Feature();
        instance.setId("ID");
        String expResult = "ID";
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Feature.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "ID";
        Feature instance = new Feature();
        instance.setId(id);
    }

    /**
     * Test of equals method, of class Feature.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Feature();
        Feature instance = new Feature();
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }


    
}
