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
