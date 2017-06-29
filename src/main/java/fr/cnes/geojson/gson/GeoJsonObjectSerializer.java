/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import fr.cnes.geojson.crs.Crs;
import fr.cnes.geojson.object.GeoJsonObject;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * Implements a serialization of the abstract GeoJSonObject object.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr
 */
public class GeoJsonObjectSerializer implements JsonSerializer<GeoJsonObject> {   

    @Override
    public JsonElement serialize(GeoJsonObject geoJsonObject, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("type", geoJsonObject.getType());
        addBbox(geoJsonObject, object, context);
        addCrs(geoJsonObject, object, context);
        addForeignMembers(geoJsonObject, object, context);        
        return object;
    }
    
    /**
     * Adds bounding box to the JSON object when bbox is defined. bbox is an
     * array of double[] and it is serialized by the context as numbers.
     *
     * @param geoJsonObject GeoJsonObject object
     * @param object JSON object
     * @param context context to serialize complex type
     */
    private void addBbox(final GeoJsonObject geoJsonObject, final JsonObject object, final JsonSerializationContext context) {
        if (geoJsonObject.getBbox() != null) {
            object.add("bbox", context.serialize(geoJsonObject.getBbox()));
        }
    }    
    
    /**
     * Adds Crs to the JSON object when crs is defined.
     *
     * @param geoJsonObject GeoJsonObject object
     * @param object JSON object
     * @param context context to serialize complex type
     */
    private void addCrs(final GeoJsonObject geoJsonObject, final JsonObject object, final JsonSerializationContext context) {
        if (geoJsonObject.getCrs() != null) {
            object.add("crs", context.serialize(geoJsonObject.getCrs(), Crs.class));
        }
    }

    /**
     * Adds Foreign members to the JSON object when foreignMembers map is
     * defined.
     *
     * @param geoJsonObject GeoJsonObject object
     * @param object JSON object
     * @param context context to serialize complex type
     */
    private void addForeignMembers(final GeoJsonObject geoJsonObject, final JsonObject object, final JsonSerializationContext context) {
        Set<String> foreignMembers = geoJsonObject.getForeignMembers().keySet();
        foreignMembers.stream().forEach((name) -> {
            object.add(name, context.serialize(geoJsonObject.getForeignMembers().get(name)));
        });
    }    
}
