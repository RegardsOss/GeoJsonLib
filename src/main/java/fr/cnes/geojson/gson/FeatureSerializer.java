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
 *****************************************************************************
 */
package fr.cnes.geojson.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.cnes.geojson.object.Feature;
import fr.cnes.geojson.object.GeoJsonObject;
import fr.cnes.geojson.object.Geometry;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Implements a serialization for Feature.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class FeatureSerializer implements JsonSerializer<Feature> {

    private static final Logger LOGGER = Logger.getLogger(FeatureSerializer.class.getName());    

    @Override
    public JsonElement serialize(Feature feature, Type type, JsonSerializationContext context) {
        JsonObject object = (JsonObject) context.serialize(feature, GeoJsonObject.class);        
        addID(feature, object);
        addGeometry(feature, object, context);
        addProperties(feature, object, context);
        return object;
    }

    /**
     * Adds ID to the JSON object when ID is defined.
     *
     * @param feature Feature object to serialize
     * @param object JSON object
     */
    private void addID(final Feature feature, final JsonObject object) {
        if (feature.getId() != null) {
            object.addProperty("id", feature.getId());
        }
    }

    /**
     * Adds Geometry to the JSON object when geometry is defined.
     *
     * @param feature Feature object
     * @param object JSON object
     * @param context context to serialize complex type
     */
    private void addGeometry(final Feature feature, final JsonObject object, final JsonSerializationContext context) {
        object.add("geometry", context.serialize(feature.getGeometry(), Geometry.class));
    }

    /**
     * Adds Properties to the JSON object when properties is defined. When
     * properties does not exist of have no members, then the properties is set
     * to null.
     *
     * @param feature Feature object
     * @param object JSON object
     * @param context context to serialize complex type
     */
    private void addProperties(final Feature feature, final JsonObject object, final JsonSerializationContext context) {
        JsonElement properties = JsonNull.INSTANCE;
        if (feature.getProperties() != null) {
            Set<String> propNames = feature.getProperties().keySet();
            properties = buildPropertiesObject(propNames, feature, context);
        }
        object.add("properties", properties);
    }

    /**
     * Builds the properties JSON object.
     * @param propNames keys to set
     * @param feature Feature object to serialize
     * @param context context to serialize complex type
     * @return The properties JSoON object
     */
    private JsonElement buildPropertiesObject(final Set<String> propNames, final Feature feature, final JsonSerializationContext context) {
        JsonElement properties = JsonNull.INSTANCE;
        if (propNames.size() > 0) {
            JsonObject propsObj = new JsonObject();
            propNames.stream().forEach((name) -> {
                propsObj.add(name, context.serialize(feature.getProperties().get(name)));
            });
            properties = propsObj;
        }
        return properties;
    }

}
