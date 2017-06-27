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

import fr.cnes.geojson.crs.Crs;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public abstract class GeoJsonObject {
    
    private String type;
    private Crs crs = null;
    private double[] bbox = null;
    public Map<String, Object> foreignMembers = new HashMap<>();
    
    /**
     * Creates a empty GeoJson object.
     * @param type GeoJson type
     */
    protected GeoJsonObject(String type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the crs
     */
    public Crs getCrs() {
        return crs;
    }

    /**
     * @param crs the crs to set
     */
    public void setCrs(Crs crs) {
        this.crs = crs;
    }

    /**
     * @return the bbox
     */
    public double[] getBbox() {
        return bbox;
    }

    /**
     * @param bbox the bbox to set
     */
    public void setBbox(double[] bbox) {
        this.bbox = bbox;
    }

    /**
     * @return the foreignMembers
     */
    public Map<String, Object> getForeignMembers() {
        return foreignMembers;
    }

    /**
     * @param foreignMembers the foreignMembers to set
     */
    public void setForeignMembers(Map<String, Object> foreignMembers) {
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
