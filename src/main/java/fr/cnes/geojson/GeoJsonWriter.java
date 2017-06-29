/**
 * ****************************************************************************
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
 * ****************************************************************************
 */
package fr.cnes.geojson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.cnes.geojson.gson.DoubleNaNSerializer;
import fr.cnes.geojson.gson.FeatureCollectionSerializer;
import fr.cnes.geojson.gson.FeatureSerializer;
import fr.cnes.geojson.gson.FloatNaNSerializer;
import fr.cnes.geojson.gson.GeometrySerializer;
import fr.cnes.geojson.object.Feature;
import fr.cnes.geojson.object.FeatureCollection;
import fr.cnes.geojson.object.Geometry;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates a GeoJSON writer.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class GeoJsonWriter implements WriterOptions {

    /**
     * Option for display the GeoJSON is an pretty way.
     */
    public static final String PRETTY_DISPLAY = "pretty_display";
    
    /**
     * Option to sort correctly the points in a polygon.
     * A polygon is a set of linear ring. The first ring is the exterior ring, 
     * the points are oriented in a counterclockwise way. The other rings are
     * interior rings (holes), the points are oriented in a clockwise way.
     */
    public static final String FIX_CLOCKWISE = "fix_clockwise";
    
    /**
     * Option to close the polygon if it is not.
     */
    public static final String CLOSE_POLYGON = "close_polygon";
    
    /**
     * Option for shift all longitudes in the range [-180, 180].
     */
    public static final String FIX_LONGITUDE = "fix_longitude";
    
    /**
     * Option to convert all NaN (Not a Number) to null.
     */
    public static final String FIX_NAN = "fix_nan";    
    
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(GeoJsonWriter.class.getName());
    
    /**
     * Options for GeoJsonWriter.
     * The available options are :
     * <ul>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#CLOSE_POLYGON}</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#FIX_CLOCKWISE}</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#FIX_LONGITUDE}</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#FIX_NAN}</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#PRETTY_DISPLAY}</li>
     * </ul>
     */
    private final Map<String, Object> options;

    /**
     * Creates a GeoJson writer based on customized options.     
     * @param options Options for writer.
     */
    public GeoJsonWriter(final Map<String, Object> options) {
        LOGGER.entering(GeoJsonWriter.class.getName(), "Constructor", options);
        this.options = options;
    }

    /**
     * Creates a GeoJson writer based on default options.
     * The default options are:
     * <ul>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#PRETTY_DISPLAY}=false</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#FIX_CLOCKWISE}=false</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#CLOSE_POLYGON}=false</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#FIX_LONGITUDE}=false</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#FIX_NAN}=false</li>          
     * </ul>
     */
    public GeoJsonWriter() {
        LOGGER.entering(GeoJsonWriter.class.getName(), "Constructor");        
        this.options = new HashMap<>();
        this.options.put(PRETTY_DISPLAY, false);
        this.options.put(FIX_CLOCKWISE, false);
        this.options.put(CLOSE_POLYGON, false);
        this.options.put(FIX_LONGITUDE, false);
        this.options.put(FIX_NAN, false);
        LOGGER.finer(String.format("Default options fot GeoJsonWriter: %s", this.options));
    }

    /**
     * Sets the options for GeoJsonWriter.
     * Clear the current options and set the new ones.
     * @param options options
     */
    @Override
    public void setOptions(final Map<String, Object> options) {
        this.options.clear();
        this.options.putAll(options);
    }

    /**
     * Returns the current options.
     * @return the options
     */
    @Override
    public Map<String, Object> getOptions() {
        return this.options;
    }

    /**
     * Converts a FeatureCollection to GeoJSON.     
     * @param featureCollection object to convert to GeoJSON
     * @return a GeoJSON String
     */
    public String toJson(final FeatureCollection featureCollection) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Geometry.class, new GeometrySerializer(this.options))
                .registerTypeAdapter(Feature.class, new FeatureSerializer())
                .registerTypeAdapter(FeatureCollection.class, new FeatureCollectionSerializer())
                .registerTypeAdapter(Double.class, new DoubleNaNSerializer())
                .registerTypeAdapter(Float.class, new FloatNaNSerializer())                
                .serializeNulls()
                .serializeSpecialFloatingPointValues();
        if ((boolean) this.getOptions().get(PRETTY_DISPLAY)) {           
            gsonBuilder = gsonBuilder.setPrettyPrinting();
        }
        Gson gson = gsonBuilder.create();
        return gson.toJson(featureCollection);
    }

    /**
     * Converts a Feature to GeoJSON.     
     * @param feature object to convert to GeoJSON
     * @return a GeoJSON
     */
    public String toJson(final Feature feature) {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Geometry.class, new GeometrySerializer(this.options))
                .registerTypeAdapter(Feature.class, new FeatureSerializer())
                .registerTypeAdapter(FeatureCollection.class, new FeatureCollectionSerializer())
                .registerTypeAdapter(Double.class, new DoubleNaNSerializer())
                .registerTypeAdapter(Float.class, new FloatNaNSerializer())                                
                .serializeNulls()
                .serializeSpecialFloatingPointValues();
        if ((boolean) this.getOptions().get(PRETTY_DISPLAY)) {
            gsonBuilder = gsonBuilder.setPrettyPrinting();
        }
        Gson gson = gsonBuilder.create();
        return gson.toJson(feature);
    }

    /**
     * Creates a feature.
     * When the feature is created, the {@link fr.cnes.geojson.GeoJsonWriter#options options}
     * are passed as parameter.     
     * @return the feature
     */
    public Feature createFeature() {
        return new Feature(options);
    }

    /**
     * Creates a feature collection.
     * When the feature collection is created, the {@link fr.cnes.geojson.GeoJsonWriter#options options}
     * are passed as parameter.
     * @return the feature collection
     */
    public FeatureCollection createFeatureCollection() {
        return new FeatureCollection(options);
    }

    /**
     * Creates an empty generic geometry.
     * When the geometry is created, the {@link fr.cnes.geojson.GeoJsonWriter#options options}
     * are passed as parameter.
     * @param <T> generic geometry (Point, MultiPoint, LineString, ...)
     * @param clazz geometry to create
     * @return An empty geometry without any points.
     */
    public <T extends Geometry> T createGeometry(final Class<? extends Geometry> clazz) {
        return Geometry.createGeometry(clazz, options);
    }

    /**
     * Creates a filled generic geometry.
     * When the geometry is created, the {@link fr.cnes.geojson.GeoJsonWriter#options options}
     * are passed as parameter.
     * @param <T> generic geometry (Point, MultiPoint, LineString, ...)
     * @param clazz geometry to create
     * @param coordinates the coordinates to fill the geometry
     * @return A geometry with points
     */    
    public <T extends Geometry> T createGeometry(final Class<? extends Geometry> clazz, Object coordinates) {
        T geom = Geometry.createGeometry(clazz, options);
        geom.setCoordinates(coordinates);
        return geom;
    }

}
