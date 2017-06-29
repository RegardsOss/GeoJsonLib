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
 * Provides the classes representing the GeoJson Objects.
 * 
 * Here is the different GeoJSON object as defined in the 
 * <a href="https://tools.ietf.org/html/rfc7946#page-6">GeoJSON format</a>.
 * <ul>
 * <li>{@link fr.cnes.geojson.object.GeoJsonObject} : abstract class</li>
 * <li>{@link fr.cnes.geojson.object.Geometry} : concrete Geometry class</li>
 * <li>{@link fr.cnes.geojson.object.Feature} : concrete Feature class</li>
 * <li>{@link fr.cnes.geojson.object.FeatureCollection} : concrete FeatureCollection class</li>
 * </ul>
 * <pre>
 *                  GeoJsonObject
 *                       ^
 *                       |
 *          ----------------------------
 *          |            |             |
 *      Geometry      Feature       FeatureCollection
 * </pre>
 * A FeatureCollection is mainly a collection of Feature objects. A Feature 
 * mainly contains a geometry and some properties.
 */
package fr.cnes.geojson.object;
