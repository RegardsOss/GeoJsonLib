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
public class MultiPolygonTest {

    public MultiPolygonTest() {
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
     * Test of setPoints method, of class MultiPolygon.
     */
    @Test
    public void testSetPoints_doubleArrArrArrArr() {
        System.out.println("setPoints");
        double[][][][] points = new double[][][][]{
            {{{102.0, 2.0}, {103.0, 2.0}, {103.0, 3.0}, {102.0, 3.0}, {102.0, 2.0}}},
            {{{100.0, 0.0}, {101.0, 0.0}, {101.0, 1.0}, {100.0, 1.0}, {100.0, 0.0}},
            {{100.2, 0.2}, {100.8, 0.2}, {100.8, 0.8}, {100.2, 0.8}, {100.2, 0.2}}}
        };
        MultiPolygon instance = new MultiPolygon();
        instance.setPoints(points);
        assertNotNull(instance.getCoordinates());
    }

    /**
     * Test of setPoints method, of class MultiPolygon.
     */
    @Test
    public void testSetPoints_PolygonArr() {
        System.out.println("setPoints");
        Polygon[] polygons = new Polygon[]{
            new Polygon(new double[][][]{{{102.0, 2.0}, {103.0, 2.0}, {103.0, 3.0}, {102.0, 3.0}, {102.0, 2.0}}}),
            new Polygon(new double[][][]{{{100.0, 0.0}, {101.0, 0.0}, {101.0, 1.0}, {100.0, 1.0}, {100.0, 0.0}}}),
            new Polygon(new double[][][]{{{100.2, 0.2}, {100.8, 0.2}, {100.8, 0.8}, {100.2, 0.8}, {100.2, 0.2}}})
        };
        MultiPolygon instance = new MultiPolygon();
        instance.setPoints(polygons);
        assertArrayEquals(instance.getPoints(Polygon[].class), polygons);
    }

    /**
     * Test of getPoints method, of class MultiPolygon.
     */
    @Test
    public void testGetPoints() {
        System.out.println("getPoints");
        MultiPolygon instance = new MultiPolygon();
        double[][][][] points = new double[][][][]{
            {{{102.0, 2.0}, {103.0, 2.0}, {103.0, 3.0}, {102.0, 3.0}, {102.0, 2.0}}},
            {{{100.0, 0.0}, {101.0, 0.0}, {101.0, 1.0}, {100.0, 1.0}, {100.0, 0.0}},
            {{100.2, 0.2}, {100.8, 0.2}, {100.8, 0.8}, {100.2, 0.8}, {100.2, 0.2}}}
        };
        instance.setPoints(points);
        Object expResult = points;
        Object result = instance.getPoints(double[][][][].class);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCoordinates method, of class MultiPolygon.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        System.out.println("getPoints");
        MultiPolygon instance = new MultiPolygon();
        double[][][][] points = new double[][][][]{
            {{{102.0, 2.0}, {103.0, 2.0}, {103.0, 3.0}, {102.0, 3.0}, {102.0, 2.0}}},
            {{{100.0, 0.0}, {101.0, 0.0}, {101.0, 1.0}, {100.0, 1.0}, {100.0, 0.0}},
            {{100.2, 0.2}, {100.8, 0.2}, {100.8, 0.8}, {100.2, 0.8}, {100.2, 0.2}}}
        };
        instance.setPoints(points);
        Object expResult = points;
        Object result = instance.getCoordinates();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class MultiPolygon.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = new MultiPolygon(new double[][][][]{{
            {{10, 10}, {11, 10}, {10, 10}},
            {{10, 10}, {11, 10}, {10, 10}}
        }});
        MultiPolygon instance = new MultiPolygon();
        instance.setCoordinates(new double[][][][]{{
            {{10, 10}, {11, 10}, {10, 10}},
            {{10, 10}, {11, 10}, {10, 10}}
        }});
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of not equals method, of class MultiPolygon.
     */
    @Test
    public void testNotEquals() {
        System.out.println("equals");
        Object obj = new MultiPolygon(new double[][][][]{
            {           
                {{10, 10}, {11, 10.5}, {11, 11},{10,11},{10,10}}
            },
            {
                {{10, 10}, {11, 10}, {11, 11},{10,11},{10,10}}
            }
        });
        MultiPolygon instance = new MultiPolygon();
        instance.setCoordinates(new double[][][][]{
            {           
                {{10, 10}, {11, 10}, {11, 11},{10,11},{10,10}}
            },
            {
                {{10, 10}, {11, 10}, {11, 11},{10,11},{10,10}}
            }
        });
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    /**
     * Test of setCoordinates method, of class MultiPolygon.
     */
    @Test
    public void testSetCoordinates() {
        System.out.println("setCoordinates");
        Object coordinates = new double[][][][]{
            {
                {{10, 10}, {11, 10}, {11, 11}, {10, 11}, {10, 10}}
            },
            {
                {{10, 10}, {11, 10}, {11, 11}, {10, 11}, {10, 10}}
            }
        };
        MultiPolygon instance = new MultiPolygon();
        instance.setCoordinates(coordinates);
    }

    /**
     * Test of computeBbox method, of class MultiPolygon.
     */
    @Test
    public void testComputeBbox() {
        System.out.println("computeBbox");
        MultiPolygon instance = new MultiPolygon();
        instance.setCoordinates(new double[][][][]{
            {
                {{10, 10}, {11, 10}, {11, 11}, {10, 11}, {10, 10}}
            },
            {
                {{10, 10}, {11, 10}, {11, 11}, {10, 11}, {10, 10}}
            }
        });
        instance.computeBbox();
        double[] bbox = instance.getBbox();
        assertArrayEquals(new double[]{10, 10, 11, 11}, bbox, 0.0);
    }

}
