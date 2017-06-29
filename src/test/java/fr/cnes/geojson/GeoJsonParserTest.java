/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson;

import fr.cnes.geojson.object.Feature;
import fr.cnes.geojson.object.FeatureCollection;
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
public class GeoJsonParserTest {
    
    GeoJsonParser parser;
    GeoJsonWriter writer;

    public GeoJsonParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        parser = new GeoJsonParser();
        writer = new GeoJsonWriter();
        writer.getOptions().put(GeoJsonWriter.PRETTY_DISPLAY, true);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parse method, of class GeoJsonParser.
     */
    @Test
    public void testParse() {
        System.out.println("parse");
        String expResult = "{\n"
                + "  \"type\": \"Feature\",\n"
                + "  \"crs\": {\n"
                + "    \"type\": \"name\",\n"
                + "    \"properties\": {\n"
                + "      \"urn\": \"urn:ogc:def:crs:OGC:1.3:CRS84\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"id\": \"01\",\n"                
                + "  \"geometry\": {\n"
                + "    \"type\": \"Polygon\",\n"
                + "    \"coordinates\": [\n"
                + "      [\n"
                + "        [\n"
                + "          2.873268127441,\n"
                + "          42.702655792236\n"
                + "        ],\n"
                + "        [\n"
                + "          2.87446975708,\n"
                + "          42.682914733886\n"
                + "        ],\n"
                + "        [\n"
                + "          2.8811645507,\n"
                + "          42.703514099121\n"
                + "        ],\n"
                + "        [\n"
                + "          2.873268127441,\n"
                + "          42.702655792236\n"
                + "        ]\n"
                + "      ]\n"
                + "    ]\n"
                + "  },\n"
                + "  \"properties\": null\n"
                + "}";
        Feature feature = parser.parse(expResult);
        String result = writer.toJson(feature);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of parse method, of class GeoJsonParser.
     */
    @Test
    public void testParse2() {
        System.out.println("parse");
        String expResult = "{\n"
                + "  \"type\": \"FeatureCollection\",\n"
                + "  \"features\": [\n"
                + "    {\n"
                + "      \"type\": \"Feature\",\n"
                + "      \"geometry\": {\n"
                + "        \"type\": \"Point\",\n"
                + "        \"coordinates\": [\n"
                + "          102.0,\n"
                + "          0.5\n"
                + "        ]\n"
                + "      },\n"
                + "      \"properties\": {\n"
                + "        \"prop0\": \"value0\"\n"
                + "      }\n"
                + "    },\n"
                + "    {\n"
                + "      \"type\": \"Feature\",\n"
                + "      \"geometry\": {\n"
                + "        \"type\": \"LineString\",\n"
                + "        \"coordinates\": [\n"
                + "          [\n"
                + "            102.0,\n"
                + "            0.0\n"
                + "          ],\n"
                + "          [\n"
                + "            103.0,\n"
                + "            1.0\n"
                + "          ],\n"
                + "          [\n"
                + "            104.0,\n"
                + "            0.0\n"
                + "          ],\n"
                + "          [\n"
                + "            105.0,\n"
                + "            1.0\n"
                + "          ]\n"
                + "        ]\n"
                + "      },\n"
                + "      \"properties\": {\n"
                + "        \"value2\": 0.0\n"
                + "      }\n"
                + "    },\n"
                + "    {\n"
                + "      \"type\": \"Feature\",\n"
                + "      \"geometry\": {\n"
                + "        \"type\": \"Polygon\",\n"
                + "        \"coordinates\": [\n"
                + "          [\n"
                + "            [\n"
                + "              100.0,\n"
                + "              0.0\n"
                + "            ],\n"
                + "            [\n"
                + "              101.0,\n"
                + "              0.0\n"
                + "            ],\n"
                + "            [\n"
                + "              101.0,\n"
                + "              1.0\n"
                + "            ],\n"
                + "            [\n"
                + "              100.0,\n"
                + "              1.0\n"
                + "            ],\n"
                + "            [\n"
                + "              100.0,\n"
                + "              0.0\n"
                + "            ]\n"
                + "          ]\n"
                + "        ]\n"
                + "      },\n"
                + "      \"properties\": {\n"
                + "        \"prop0\": \"value0\",\n"
                + "        \"this\": \"that\"\n"
                + "      }\n"
                + "    }\n"
                + "  ]\n"
                + "}";
        FeatureCollection featureColl = parser.parse(expResult);
        String result = writer.toJson(featureColl);
        System.out.println(result);
        assertEquals(expResult, result);        
    }    

    /**
     * Test of isFeatureCollection method, of class GeoJsonParser.
     */
    @Test
    public void testIsFeatureCollection() {
        System.out.println("isFeatureCollection");
        String geojson = "{\n"
                + "  \"type\": \"FeatureCollection\",\n"
                + "  \"features\": [\n"
                + "    {\n"
                + "      \"type\": \"Feature\",\n"
                + "      \"geometry\": {\n"
                + "        \"type\": \"Point\",\n"
                + "        \"coordinates\": [\n"
                + "          102.0,\n"
                + "          0.5\n"
                + "        ]\n"
                + "      },\n"
                + "      \"properties\": {\n"
                + "        \"prop0\": \"value0\"\n"
                + "      }\n"
                + "    },\n"
                + "    {\n"
                + "      \"type\": \"Feature\",\n"
                + "      \"geometry\": {\n"
                + "        \"type\": \"LineString\",\n"
                + "        \"coordinates\": [\n"
                + "          [\n"
                + "            102.0,\n"
                + "            0.0\n"
                + "          ],\n"
                + "          [\n"
                + "            103.0,\n"
                + "            1.0\n"
                + "          ],\n"
                + "          [\n"
                + "            104.0,\n"
                + "            0.0\n"
                + "          ],\n"
                + "          [\n"
                + "            105.0,\n"
                + "            1.0\n"
                + "          ]\n"
                + "        ]\n"
                + "      },\n"
                + "      \"properties\": {\n"
                + "        \"value2\": 0.0\n"
                + "      }\n"
                + "    },\n"
                + "    {\n"
                + "      \"type\": \"Feature\",\n"
                + "      \"geometry\": {\n"
                + "        \"type\": \"Polygon\",\n"
                + "        \"coordinates\": [\n"
                + "          [\n"
                + "            [\n"
                + "              100.0,\n"
                + "              0.0\n"
                + "            ],\n"
                + "            [\n"
                + "              101.0,\n"
                + "              0.0\n"
                + "            ],\n"
                + "            [\n"
                + "              101.0,\n"
                + "              1.0\n"
                + "            ],\n"
                + "            [\n"
                + "              100.0,\n"
                + "              1.0\n"
                + "            ],\n"
                + "            [\n"
                + "              100.0,\n"
                + "              0.0\n"
                + "            ]\n"
                + "          ]\n"
                + "        ]\n"
                + "      },\n"
                + "      \"properties\": {\n"
                + "        \"prop0\": \"value0\",\n"
                + "        \"this\": \"that\"\n"
                + "      }\n"
                + "    }\n"
                + "  ]\n"
                + "}";
        boolean expResult = true;
        boolean result = GeoJsonParser.isFeatureCollection(geojson);
        assertEquals(expResult, result);
    }


}
