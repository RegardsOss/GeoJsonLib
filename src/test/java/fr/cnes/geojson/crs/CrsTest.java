/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson.crs;

import fr.cnes.geojson.crs.Crs.TypeEnum;
import java.util.HashMap;
import java.util.Map;
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
public class CrsTest {
    
    public CrsTest() {
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
     * Test of getType method, of class Crs.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        Crs instance = new Crs();
        String expResult = null;
        TypeEnum result = instance.getType();
        assertNull(result);
    }

    /**
     * Test of setType method, of class Crs.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        TypeEnum type = TypeEnum.name;
        Crs instance = new Crs();
        instance.setType(type);
        assertEquals(instance.getType(), type);
    }

    /**
     * Test of getProperties method, of class Crs.
     */
    @Test
    public void testGetProperties() {
        System.out.println("getProperties");
        Crs instance = new Crs();
        Map<String, Object> result = instance.getProperties();
        assertNotNull(result);
    }

    /**
     * Test of setProperties method, of class Crs.
     */
    @Test
    public void testSetProperties() {
        System.out.println("setProperties");
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");
        Crs instance = new Crs();
        instance.setProperties(properties);
        assertEquals(instance.getProperties().get("name"), "urn:ogc:def:crs:OGC:1.3:CRS84");
    }

    /**
     * Test of equals method, of class Crs.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new Crs();
        Crs instance = new Crs();
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
}
