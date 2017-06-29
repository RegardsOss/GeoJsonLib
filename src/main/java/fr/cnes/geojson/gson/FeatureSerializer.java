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

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.cnes.geojson.crs.Crs;
import fr.cnes.geojson.object.Feature;
import fr.cnes.geojson.object.Geometry;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Provides the implementation of the serialization for Feature.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class FeatureSerializer implements JsonSerializer<Feature> {

    private static final Logger LOGGER = Logger.getLogger(FeatureSerializer.class.getName());    
    
    @Override
    public JsonElement serialize(Feature feature, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("type", feature.getType());

        // add id
        if (feature.getId() != null) {
            object.addProperty("id", feature.getId());
        }

        if (feature.getBbox() != null) {
            object.add("bbox", context.serialize(feature.getBbox()));
        }

        if (feature.getCrs() != null) {
            object.add("crs", context.serialize(feature.getCrs(), Crs.class));
        }

        // add geometry
        // force to go through GeoObject serializer
        object.add("geometry", context.serialize(feature.getGeometry(), Geometry.class));

        // add properties
        JsonElement properties = JsonNull.INSTANCE;
        if (feature.getProperties() != null) {
            Set<String> propNames = feature.getProperties().keySet();
            if (propNames.size() > 0) {
                JsonObject propsObj = new JsonObject();
                for (String name : propNames) {
                    propsObj.add(name, context.serialize(feature.getProperties().get(name)));
                }
                properties = propsObj;
            }
        }
        object.add("properties", properties);

        Set<String> foreignMembers = feature.getForeignMembers().keySet();
        for (String name : foreignMembers) {
            object.add(name, context.serialize(feature.getForeignMembers().get(name)));
        }

        return object;
    }
}
