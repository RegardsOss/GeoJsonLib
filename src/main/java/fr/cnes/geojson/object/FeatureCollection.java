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
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * A GeoJSON object with the type "FeatureCollection" is a FeatureCollection 
 * object.  
 * A FeatureCollection object has a member with the name "features".  The value 
 * of "features" is a JSON array. Each element of the array is a Feature object 
 * as defined above.  It is possible for this array to be empty.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class FeatureCollection extends GeoJsonObject {

    /**
     * Type of the GeoJson object {@value}.
     */
    public static final String TYPE = "FeatureCollection";
    
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FeatureCollection.class.getName());    
    
    /**
     * Set of features.
     */
    public List<Feature> features = new ArrayList<>();
    
    /**
     * Creates a FeatureCollection with {@link fr.cnes.geojson.AbstractGeoJsonUtility#options options}
     * from GeoJsonWriter
     * @param options the options
     */
    public FeatureCollection(final Map<String, Object> options) {
        this();
        LOGGER.entering(FeatureCollection.class.getName(), "Constructor",options);
        this.setOptions(options);
        LOGGER.exiting(FeatureCollection.class.getName(), "Constructor");        
    }    

    /**
     * Creates an empty FeatureCollection.
     */
    protected FeatureCollection() {
        super(TYPE);
        LOGGER.entering(FeatureCollection.class.getName(), "Constructor");        
        LOGGER.exiting(FeatureCollection.class.getName(), "Constructor");        
    }

    /**
     * Returns the features.
     * @return the features
     */
    public List<Feature> getFeatures() {
        LOGGER.entering(FeatureCollection.class.getName(), "getFeatures");        
        LOGGER.exiting(FeatureCollection.class.getName(), "getFeatures", features);        
        return features;
    }

    /**
     * Sets the features.
     * @param features the feature to set
     */
    public void setFeatures(final List<Feature> features) {
        LOGGER.entering(FeatureCollection.class.getName(), "setFeatures", features);                
        this.features = features;
        LOGGER.exiting(FeatureCollection.class.getName(), "setFeatures");        
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
        return this.getFeatures().equals(fc.getFeatures());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + super.hashCode();
        hash = 83 * hash + Objects.hashCode(this.features);
        return hash;
    }

}
