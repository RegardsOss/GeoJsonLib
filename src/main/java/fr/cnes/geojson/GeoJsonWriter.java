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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.cnes.geojson.gson.FeatureCollectionSerializer;
import fr.cnes.geojson.gson.FeatureSerializer;
import fr.cnes.geojson.gson.GeometrySerializer;
import fr.cnes.geojson.object.Feature;
import fr.cnes.geojson.object.FeatureCollection;
import fr.cnes.geojson.object.Geometry;

/**
 * Creates a GeoJSON writer.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class GeoJsonWriter {

    /**
     * Converts a FeatureCollection to GeoJSON.
     * @param featureCollection object to convert to GeoJSON
     * @return a GeoJSON
     */
    public static String toJson(final FeatureCollection featureCollection) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Geometry.class, new GeometrySerializer())
                .registerTypeAdapter(Feature.class, new FeatureSerializer())
                .registerTypeAdapter(FeatureCollection.class, new FeatureCollectionSerializer())                                
                .setPrettyPrinting()
                .serializeNulls()
                .serializeSpecialFloatingPointValues()
                .create();
        return gson.toJson(featureCollection);
    }
    
    /**
     * Converts a Feature to GeoJSON.
     * @param feature object to convert to GeoJSON
     * @return a GeoJSON
     */
    public static String toJson(final Feature feature) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Geometry.class, new GeometrySerializer())
                .registerTypeAdapter(Feature.class, new FeatureSerializer())
                .setPrettyPrinting()
                .serializeNulls()
                .serializeSpecialFloatingPointValues()
                .create();
        return gson.toJson(feature);
    }    

}
