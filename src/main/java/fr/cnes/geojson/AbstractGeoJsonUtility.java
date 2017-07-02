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
package fr.cnes.geojson;

import com.google.gson.GsonBuilder;
import fr.cnes.geojson.gson.DoubleNaNSerializer;
import fr.cnes.geojson.gson.FeatureCollectionSerializer;
import fr.cnes.geojson.gson.FeatureSerializer;
import fr.cnes.geojson.gson.FloatNaNSerializer;
import fr.cnes.geojson.gson.GeoJsonObjectSerializer;
import fr.cnes.geojson.gson.GeometrySerializer;
import fr.cnes.geojson.object.Feature;
import fr.cnes.geojson.object.FeatureCollection;
import fr.cnes.geojson.object.GeoJsonObject;
import fr.cnes.geojson.object.Geometry;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract GeoJson utility used to to create a parser or a writer
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public abstract class AbstractGeoJsonUtility {

    /**
     * Options for GeoJsonWriter. The available options are :
     * <ul>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#CLOSE_POLYGON}</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#FIX_CLOCKWISE}</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#FIX_LONGITUDE}</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#FIX_NAN}</li>
     * <li>{@link fr.cnes.geojson.GeoJsonWriter#PRETTY_DISPLAY}</li>
     * </ul>
     */
    private final Map<String, Object> options = new HashMap<>();

    /**
     * Builder to construct a Gson instance with specific options.
     */
    private GsonBuilder gsonBuilder;

    /**
     * Init the GeoJson utility.
     */
    protected AbstractGeoJsonUtility() {
        gsonBuilder = new GsonBuilder();
    }

    /**
     * Init the GeoJson utility with some {@link fr.cnes.geojson.AbstractGeoJsonUtility#options options}.
     * @param options Specific options for GsonBuilder
     */
    protected AbstractGeoJsonUtility(final Map<String, Object> options) {
        this();
        Utils.checkNotNull(options);
        this.setOptions(options);
    }

    /**
     * Sets the options for GeoJsonWriter. 
     * Clear the current options and set the new ones.     
     * @param options options
     */
    public final void setOptions(final Map<String, Object> options) {
        this.options.clear();
        this.options.putAll(options);
    }

    /**
     * Returns the current options.     
     * @return the options
     */
    public Map<String, Object> getOptions() {
        return this.options;
    }

    /**
     * Returns the Gson builder with the specific options.
     * @return the gson builder
     */
    public final GsonBuilder getGsonBuilder() {
        return this.gsonBuilder.registerTypeAdapter(GeoJsonObject.class, new GeoJsonObjectSerializer())
                .registerTypeAdapter(Geometry.class, new GeometrySerializer(this.options))
                .registerTypeAdapter(Feature.class, new FeatureSerializer())
                .registerTypeAdapter(FeatureCollection.class, new FeatureCollectionSerializer())
                .registerTypeAdapter(Double.class, new DoubleNaNSerializer())
                .registerTypeAdapter(Float.class, new FloatNaNSerializer())
                .serializeNulls()
                .serializeSpecialFloatingPointValues();
    }

}
