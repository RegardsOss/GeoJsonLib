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
package fr.cnes.geojson.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.cnes.geojson.object.FeatureCollection;
import fr.cnes.geojson.object.GeoJsonObject;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implements a serialization for FeatureCollection.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class FeatureCollectionSerializer implements JsonSerializer<FeatureCollection> {
    
    private static final Logger LOGGER = Logger.getLogger(FeatureCollectionSerializer.class.getName());    

    @Override
    public JsonElement serialize(FeatureCollection featureCollection, Type type, JsonSerializationContext context) {
        JsonObject object = (JsonObject) context.serialize(featureCollection, GeoJsonObject.class);        
        addFeatures(featureCollection, object, context);
        return object;
    }
    
    /**
     * Adds features to the JSON object. 
     * When features is not defined then an empty json array is set in the JSON 
     * object.
     *
     * @param featureCollection  FeatureCollection object
     * @param object JSON object
     * @param context context to serialize complex type
     */
    private void addFeatures(FeatureCollection featureCollection, JsonObject object, JsonSerializationContext context) {
         JsonElement features;
        if(featureCollection.getFeatures() != null) {
            features = context.serialize(featureCollection.getFeatures(), List.class);
        } else {
            features = new JsonArray();
        }       
        object.add("features", features);
    }            
}
