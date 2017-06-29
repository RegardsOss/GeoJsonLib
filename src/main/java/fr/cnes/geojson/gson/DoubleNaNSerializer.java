/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.cnes.geojson.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements a serialization of Double.NaN or Double.Infinite.
 * JSON standard does not allow the representation of NaN or Infinite. That's
 * why NaN or Infinite is replaced by <i>null</i>.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class DoubleNaNSerializer implements JsonSerializer<Double>{
    
    private static final Logger LOGGER = Logger.getLogger(DoubleNaNSerializer.class.getName());

    @Override
    public JsonElement serialize(Double val, Type type, JsonSerializationContext context) {                
        JsonElement elt;
        if(val.isNaN() || val.isInfinite()) {
            elt = JsonNull.INSTANCE;
            LOGGER.log(Level.WARNING, "{0} is reaplced by null.", val);
        } else {
            elt = new JsonPrimitive(val);
        }
        return elt;
    }
    
    
}
