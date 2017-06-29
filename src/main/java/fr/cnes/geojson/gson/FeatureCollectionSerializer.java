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
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.cnes.geojson.crs.Crs;
import fr.cnes.geojson.object.FeatureCollection;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Interface representing a custom serializer for FeatureCollection.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class FeatureCollectionSerializer implements JsonSerializer<FeatureCollection> {
    
    private static final Logger LOGGER = Logger.getLogger(FeatureCollectionSerializer.class.getName());    

    @Override
    public JsonElement serialize(FeatureCollection featureCollection, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("type", featureCollection.getType());


        if (featureCollection.getBbox() != null) {
            object.add("bbox", context.serialize(featureCollection.getBbox()));
        }

        if (featureCollection.getCrs() != null) {
            object.add("crs", context.serialize(featureCollection.getCrs(), Crs.class));
        }
        
        if(featureCollection.getFeatures() != null) {
            object.add("features", context.serialize(featureCollection.getFeatures(), List.class));
        }

        Set<String> foreignMembers = featureCollection.getForeignMembers().keySet();
        for (String name : foreignMembers) {
            object.add(name, context.serialize(featureCollection.getForeignMembers().get(name)));
        }
        return object;
    }
    
}
