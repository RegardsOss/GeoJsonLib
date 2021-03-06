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
package fr.cnes.geojson.object;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * A Feature object represents a spatially bounded thing.  
 * Every Feature object is a GeoJSON object no matter where it occurs in a GeoJSON
 * text.
 * <ul>
 * <li>A Feature object has a "type" member with the value "Feature".</li>
 * <li>A Feature object has a member with the name "geometry".  The value
 * of the geometry member SHALL be either a Geometry object as defined above or,
 * in the case that the Feature is unlocated, a JSON null value.</li>
 * <li>A Feature object has a member with the name "properties".  The value of 
 * the properties member is an object (any JSON object or a JSON null value).</li>
 * <li>If a Feature has a commonly used identifier, that identifier SHOULD be 
 * included as a member of the Feature object with the name "id", and the value 
 * of this member is either a JSON string or number.</li>
 * </ul>
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class Feature extends GeoJsonObject {

    /**
     * Type of the GeoJson object {@value}.
     */
    private static final String TYPE = "Feature";
    
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Feature.class.getName());    

    /**
     * Optional identifier.
     */
    private String id;
    
    /**
     * Required Geometry.
     * The member must exist. The value can be a Json Object or null.
     */
    private Geometry geometry = null;
    
    /**
     * Required Properties.
     * The member must exist. The value can be a Json Object or null     
     */
    private HashMap<String, Object> properties = new HashMap<>();
    
    /**
     * Creates a feature with {@link fr.cnes.geojson.AbstractGeoJsonUtility#options options}
     * from GeoJsonWriter
     * @param options options     
     */
    public Feature(final Map<String, Object> options) {
        this();
        LOGGER.entering(Feature.class.getName(), "Constructor", options);
        this.setOptions(options);
        LOGGER.exiting(Feature.class.getName(), "Constructor");        
    }

    /**
     * Creates an empty Feature.
     */
    protected Feature() {
        super(TYPE);
        LOGGER.entering(Feature.class.getName(), "Constructor");
        LOGGER.entering(Feature.class.getName(), "Constructor");        
    }

    /**
     * Returns the geometry.
     * @return the geometry
     */
    public Geometry getGeometry() {
        LOGGER.entering(Feature.class.getName(), "getGeometry");
        LOGGER.exiting(Feature.class.getName(), "getGeometry", geometry);
        return geometry;
    }

    /**
     * Sets the geometry
     * @param geometry the geometry to set
     */
    public void setGeometry(final Geometry geometry) {
        LOGGER.entering(Feature.class.getName(), "setGeometry", geometry);        
        LOGGER.exiting(Feature.class.getName(), "setGeometry");        
        this.geometry = geometry;
    }

    /**
     * Returns the properties.
     * @return the properties
     */
    public HashMap<String, Object> getProperties() {
        LOGGER.entering(Feature.class.getName(), "getProperties");
        LOGGER.exiting(Feature.class.getName(), "getProperties", properties);        
        return properties;
    }

    /**
     * Sets the properties.
     * @param properties the properties to set
     */
    public void setProperties(final HashMap<String, Object> properties) {
        LOGGER.entering(Feature.class.getName(), "setProperties", properties);        
        LOGGER.exiting(Feature.class.getName(), "setProperties");        
        this.properties = properties;
    }

    /**
     * Returns the ID.
     * @return the id
     */
    public String getId() {
        LOGGER.entering(Feature.class.getName(), "getId");        
        LOGGER.exiting(Feature.class.getName(), "getId", id);        
        return id;
    }

    /**
     * Sets the ID
     * @param id the id to set
     */
    public void setId(final String id) {
        LOGGER.entering(Feature.class.getName(), "setId", id);
        LOGGER.entering(Feature.class.getName(), "setId");        
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        
        Feature f = (Feature) obj;         
        if(!super.equals(f)) {
            return false;
        }
        if(this.id == null) {
            if(f.id != null) {
                return false;
            }
        } else if(!this.id.equals(f.id)) {
            return false;
        }
        if(this.geometry == null) {
            if(f.geometry != null) {
                return false;
            }
        } else if(!this.geometry.equals(f.geometry)) {
            return false;
        }        
        if(this.properties == null) {
            if(f.properties != null) {
                return false;
            }
        } else if(!this.properties.equals(f.properties)) {
            return false;
        }         

        return true;
    }     

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + super.hashCode();
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.geometry);
        hash = 17 * hash + Objects.hashCode(this.properties);
        return hash;
    }

}
