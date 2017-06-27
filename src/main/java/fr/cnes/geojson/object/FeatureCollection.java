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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A GeoJSON object with the type "FeatureCollection" is a FeatureCollection 
 * object.  
 * A FeatureCollection object has a member with the name "features".  The value 
 * of "features" is a JSON array. Each element of the array is a Feature object 
 * as defined above.  It is possible for this array to be empty.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class FeatureCollection extends GeoJsonObject {

    public static final String TYPE = "FeatureCollection";

    public List<Feature> features = new ArrayList<>();

    /**
     * Creates an empty FeatureCollection.
     */
    public FeatureCollection() {
        super(TYPE);
    }

    /**
     * Returns the features
     * @return the features
     */
    public List<Feature> getFeatures() {
        return features;
    }

    /**
     * Sets the features.
     * @param features the feature to set
     */
    public void setFeatures(final List<Feature> features) {
        this.features = features;
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
        
        FeatureCollection fc = (FeatureCollection) obj;        
        if(!super.equals(fc)) {
            return false;
        }
        if(!this.getFeatures().equals(fc.getFeatures())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + super.hashCode();
        hash = 83 * hash + Objects.hashCode(this.features);
        return hash;
    }

}
