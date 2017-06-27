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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({fr.cnes.geojson.geometry.MultiLineStringTest.class, fr.cnes.geojson.geometry.MultiPolygonTest.class, fr.cnes.geojson.geometry.PositionTest.class, fr.cnes.geojson.geometry.MultiPointTest.class, fr.cnes.geojson.geometry.LineStringTest.class, fr.cnes.geojson.geometry.GeometryCollectionTest.class, fr.cnes.geojson.geometry.PointTest.class, fr.cnes.geojson.geometry.PolygonTest.class})
public class GeometrySuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
