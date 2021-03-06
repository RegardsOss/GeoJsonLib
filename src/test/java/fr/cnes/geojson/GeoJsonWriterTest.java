/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson;

import fr.cnes.geojson.crs.Crs;
import fr.cnes.geojson.geometry.LineString;
import fr.cnes.geojson.geometry.Point;
import fr.cnes.geojson.geometry.Polygon;
import fr.cnes.geojson.object.Feature;
import fr.cnes.geojson.object.FeatureCollection;
import fr.cnes.geojson.object.Geometry;
import java.util.Arrays;
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
public class GeoJsonWriterTest {

    GeoJsonWriter writer;

    public GeoJsonWriterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        writer = new GeoJsonWriter();
        writer.getOptions().put(GeoJsonWriter.PRETTY_DISPLAY, true);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toJson method, of class GeoJsonWriter.
     */
    @Test
    public void testToJson_FeatureCollection() {
        System.out.println("toJson");
        FeatureCollection featureCollection = writer.createFeatureCollection();
        Feature feature1 = writer.createFeature();
        feature1.setGeometry(writer.createGeometry(Point.class, new double[]{102.0, 0.5}));
        feature1.getProperties().put("prop0", "value0");
        Feature feature2 = writer.createFeature();
        feature2.setGeometry(writer.createGeometry(LineString.class, new double[][]{{102.0, 0.0}, {103.0, 1.0}, {104.0, 0.0}, {105.0, 1.0}}));
        feature2.getProperties().put("value2", 0);
        Feature feature3 = writer.createFeature();
        feature3.setGeometry(writer.createGeometry(Polygon.class, new double[][][]{{{100.0, 0.0}, {101.0, 0.0}, {101.0, 1.0}, {100.0, 1.0}, {100.0, 0.0}}}));
        feature3.getProperties().put("prop0", "value0");
        feature3.getProperties().put("this", "that");
        featureCollection.setFeatures(Arrays.asList(feature1, feature2, feature3));
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
                + "        \"value2\": 0\n"
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
        String result = writer.toJson(featureCollection);
        assertEquals(expResult, result);
    }

    /**
     * Test of toJson method, of class GeoJsonWriter.
     */
    @Test
    public void testToJson_Feature() {
        System.out.println("toJson");
        Feature feature = writer.createFeature();
        String expResult = "{\n"
                + "  \"type\": \"Feature\",\n"
                + "  \"geometry\": null,\n"
                + "  \"properties\": null\n"
                + "}";
        String result = writer.toJson(feature);
        assertEquals(expResult, result);
    }

    /**
     * Test of toJson method, of class GeoJsonWriter.
     */
    @Test
    public void testToJson_Feature2() {
        System.out.println("toJson");
        Feature feature = writer.createFeature();
        Geometry geom = writer.createGeometry(Point.class);
        feature.setGeometry(geom);
        String expResult = "{\n"
                + "  \"type\": \"Feature\",\n"
                + "  \"geometry\": null,\n"
                + "  \"properties\": null\n"
                + "}";
        String result = writer.toJson(feature);
        assertEquals(expResult, result);
    }

    /**
     * Test of toJson method, of class GeoJsonWriter.
     */
    @Test
    public void testToJson_Feature3() {
        System.out.println("toJson");
        Feature feature = writer.createFeature();
        Point point = writer.createGeometry(Point.class);
        point.setCoordinates(new double[]{});
        feature.setGeometry(point);
        String expResult = "{\n"
                + "  \"type\": \"Feature\",\n"
                + "  \"geometry\": null,\n"
                + "  \"properties\": null\n"
                + "}";
        String result = writer.toJson(feature);
        assertEquals(expResult, result);
    }

    /**
     * Test of toJson method, of class GeoJsonWriter.
     */
    @Test
    public void testToJson_Feature4() {
        System.out.println("toJson");
        Feature feature = writer.createFeature();
        feature.setId("01");
        writer.getOptions().put(GeoJsonWriter.FIX_CLOCKWISE, true);
        Polygon geom = writer.createGeometry(Polygon.class, new double[][][]{
            {{2.873268127441, 42.702655792236}, {2.8811645507, 42.7035140991210}, {2.87446975708, 42.682914733886}, {2.873268127441, 42.702655792236}}
        });
        feature.setGeometry(geom);
        Crs crs = new Crs();
        crs.setType(Crs.TypeEnum.name);
        crs.getProperties().put("urn", "urn:ogc:def:crs:OGC:1.3:CRS84");
        feature.setCrs(crs);
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
        String result = writer.toJson(feature);
        assertEquals(expResult, result);
    }

    @Test
    public void testToJson_NaN() {
        System.out.println("toJson NaN");
        Feature feature = writer.createFeature();
        Map<String, Object> members = new HashMap<>();
        members.put("test", new double[]{2, 4, Double.NaN, 6});
        feature.setForeignMembers(members);
        String result = writer.toJson(feature);
        String expResult = "{\n"
                + "  \"type\": \"Feature\",\n"
                + "  \"test\": [\n"
                + "    2.0,\n"
                + "    4.0,\n"
                + "    null,\n"
                + "    6.0\n"
                + "  ],\n"
                + "  \"geometry\": null,\n"
                + "  \"properties\": null\n"
                + "}";
        assertEquals(expResult, result);
    }

}
