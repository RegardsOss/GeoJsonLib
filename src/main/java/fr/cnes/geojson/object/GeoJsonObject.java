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

import fr.cnes.geojson.WriterOptions;
import fr.cnes.geojson.crs.Crs;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * A GeoJSON object represents a Geometry, Feature, or collection of
 * Features.
 *
 * <ul>
 * <li>A GeoJSON object is a JSON object</li>
 * <li>A GeoJSON object has a member with the name "type".  The value of
 * the member MUST be one of the GeoJSON types.</li>
 * <li>A GeoJSON object MAY have a "bbox" member, the value of which MUST
 * be a bounding box array</li>
 * <li>A GeoJSON object MAY have other members</li>
 * </ul>
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public abstract class GeoJsonObject implements WriterOptions {
    
    private static final Logger LOGGER = Logger.getLogger(GeoJsonObject.class.getName());    
    
    private String type;
    private Crs crs = null;
    private double[] bbox = null;
    private Map<String, Object> foreignMembers = new HashMap<>();
    private final Map<String, Object> options = new HashMap<>();
    
    /**
     * Creates a empty GeoJson object.
     * @param type GeoJson type
     */
    protected GeoJsonObject(String type) {
        this.type = type;
    }
    

    @Override
    public final void setOptions(final Map<String, Object> options) {
        this.options.clear();
        this.options.putAll(options);
    }

    @Override
    public final Map<String, Object> getOptions() {
        return this.options;
    }    

    /**
     * Returns the type of GeoJsonObject (feature, featureCollection, geometry)
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of GeoJsonObject
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Returns the coordinate reference system
     * @return the crs
     */
    public Crs getCrs() {
        return crs;
    }

    /**
     * Sets the coordinate reference system
     * @param crs the crs to set
     */
    public void setCrs(final Crs crs) {
        this.crs = crs;
    }

    /**
     * Returns the bbox.
     * @return the bbox
     */
    public double[] getBbox() {
        return bbox;
    }

    /**
     * Sets the bbox
     * @param bbox the bbox to set
     */
    public void setBbox(double[] bbox) {
        this.bbox = bbox;
    }

    /**
     * Returns the foreignMembers.
     * @return the foreignMembers
     */
    public Map<String, Object> getForeignMembers() {
        return foreignMembers;
    }

    /**
     * Sets the foreign members.
     * @param foreignMembers the foreignMembers to set
     */
    public void setForeignMembers(final Map<String, Object> foreignMembers) {
        this.foreignMembers = foreignMembers;
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
        
        GeoJsonObject geojson = (GeoJsonObject) obj;
        if(this.bbox == null) {
            if(geojson.bbox != null) {
                return false;
            }
        } else if(!Arrays.equals(this.bbox, geojson.bbox)) {
            return false;            
        }
        if(this.crs == null) {
            if(geojson.crs != null) {
                return false;
            }
        } else if(!this.crs.equals(geojson.crs)) {
            return false;
        }        
        
        if(!this.type.equals(geojson.type)) {
            return false;
        }         
        
        if(this.foreignMembers == null) {
            if(geojson.foreignMembers != null) {
                return false;
            }
        } else if(!this.foreignMembers.equals(geojson.foreignMembers)) {
            return false;
        }
        return true;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.crs);
        hash = 97 * hash + Arrays.hashCode(this.bbox);
        hash = 97 * hash + Objects.hashCode(this.foreignMembers);
        return hash;
    }    
          
}
