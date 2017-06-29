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
 *****************************************************************************
 */
package fr.cnes.geojson.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.cnes.geojson.crs.Crs;
import fr.cnes.geojson.object.Geometry;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides the implementation of the serializer/deserializer for Geometry. 
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class GeometrySerializer implements JsonSerializer<Geometry>, JsonDeserializer<Geometry> {
    
    private static final Logger LOGGER = Logger.getLogger(GeometrySerializer.class.getName());    
    
    private Map<String, Object> options = new HashMap<>();
 
    public GeometrySerializer() {
        super();
    }

    public GeometrySerializer(final Map<String, Object> options) {
        super();
        this.options = options;
    }

    @Override
    public JsonElement serialize(Geometry geometry, Type type, JsonSerializationContext context) {
        JsonElement result;
        if (geometry.getCoordinates() == null || geometry.length() == 0) {
            result = null;
        } else {
            JsonObject object = new JsonObject();
            object.addProperty("type", geometry.getType());

            object.add("coordinates", context.serialize(geometry.getCoordinates()));

            if (geometry.getBbox() != null) {
                object.add("bbox", context.serialize(geometry.getBbox()));
            }

            if (geometry.getCrs() != null) {
                object.add("crs", context.serialize(geometry.getCrs(), Crs.class));
            }

            Set<String> foreignMembers = geometry.getForeignMembers().keySet();
            for (String name : foreignMembers) {
                object.add(name, context.serialize(geometry.getForeignMembers().get(name)));
            }
            result = object;
        }
        return result;
    }

    @Override
    public Geometry deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        String geoType = jsonObject.get("type").getAsString();
        Geometry geometry = null;
        try {
            Class<?> geometryClass = Class.forName("fr.cnes.geojson.geometry." + geoType);
            Method method = geometryClass.getMethod("getCoordinates", (Class<?>) null);
            Class returnType = method.getReturnType();
            geometry = (Geometry) geometryClass.getConstructor(Map.class).newInstance(this.options);
            geometry.setCoordinates(context.deserialize(jsonObject.get("coordinates"), returnType));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(GeometrySerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return geometry;
    }

}
