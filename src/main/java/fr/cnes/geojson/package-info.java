 /******************************************************************************
 * Copyright 2017 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
 *
 * This file is part of Regards.
 *
 * Regards is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Regards is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Regards.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
/**
 * Provides the classes necessary to create and parses GeoSJON.
 * 
 * <h2>How to parse ?</h2>
 * Consider the following Geojson:<br>
 * <code>
 *        String expResult = "{\n"<br>
 *                + "  \"type\": \"Feature\",\n"<br>
 *               + "  \"id\": \"01\",\n"<br>
 *               + "  \"crs\": {\n"<br>
 *               + "    \"type\": \"name\",\n"<br>
 *               + "    \"properties\": {\n"<br>
 *               + "      \"urn\": \"urn:ogc:def:crs:OGC:1.3:CRS84\"\n"<br>
 *               + "    }\n"<br>
 *               + "  },\n"<br>
 *               + "  \"geometry\": {\n"<br>
 *               + "    \"type\": \"Polygon\",\n"<br>
 *               + "    \"coordinates\": [\n"<br>
 *               + "      [\n"<br>
 *               + "        [\n"<br>
 *               + "          2.873268127441,\n"<br>
 *               + "          42.702655792236\n"<br>
 *               + "        ],\n"<br>
 *               + "        [\n"<br>
 *               + "          2.87446975708,\n"<br>
 *               + "          42.682914733886\n"<br>
 *               + "        ],\n"<br>
 *               + "        [\n"<br>
 *               + "          2.8811645507,\n"<br>
 *               + "          42.703514099121\n"<br>
 *               + "        ],\n"<br>
 *               + "        [\n"<br>
 *               + "          2.873268127441,\n"<br>
 *               + "          42.702655792236\n"<br>
 *               + "        ]\n"<br>
 *               + "      ]\n"<br>
 *               + "    ]\n"<br>
 *               + "  },\n"<br>
 *               + "  \"properties\": null\n"<br>
 *               + "}";<br>
 * </code><br>
 * To parse the GeoJSON, you will need to call the following method :<br>
 * <code>
 * GeoJsonParser parser = new GeoJsonParser();
 * Feature feature = parser.parse(expResult);
 * </code><br><br>
 * In the case where your GeoJson is a FeatureCollection, then you will need
 * to call :<br>
 * <code>
 * FeatureCollection featureCollection = parser.parse(expResult);  
 * </code>
 * 
 * <h2>How to write GeoJson ?</h2>
 * To write a Feature or a featureCollection, call this method:<br>
 * <code>
 * GeoJsonWriter writer = new GeoJsonWriter();
 * String result = writer.toJson(featureCollection);
 * </code>
 */
package fr.cnes.geojson;
