/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson.object;

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
public class FeatureCollectionTest {
    
    public FeatureCollectionTest() {
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
     * Test of getFeature method, of class FeatureCollection.
     */
    @Test
    public void testGetFeature() {
        System.out.println("getFeature");        
        FeatureCollection instance = new FeatureCollection();
        int expResult = 0;
        List<Feature> result = instance.getFeatures();
        assertEquals(expResult, result.size());
    }

    /**
     * Test of setFeature method, of class FeatureCollection.
     */
    @Test
    public void testSetFeature() {
        System.out.println("setFeature");
        Feature feature = new Feature();        
        List<Feature> features = Arrays.asList(feature);
        FeatureCollection instance = new FeatureCollection();
        instance.setFeatures(features);
    }


    /**
     * Test of equals method, of class FeatureCollection.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new FeatureCollection();
        FeatureCollection instance = new FeatureCollection();
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }
    
}
