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
package fr.cnes.geojson.geometry;

import fr.cnes.geojson.Utils;
import fr.cnes.geojson.object.Geometry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Provides a list of geometries.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class GeometryCollection extends Geometry {
    
    private static final String GEOMETRY_COLLECTION_STRING = "GeometryCollection";
    private static final Logger LOGGER = Logger.getLogger(GeometryCollection.class.getName());
    
    /**
     * Creates an empty geometry collection.
     */
    protected GeometryCollection() {
        super(GEOMETRY_COLLECTION_STRING);
        LOGGER.entering(GeometryCollection.class.getName(), "Constructor");
        this.coordinates = new ArrayList<>();
        LOGGER.exiting(GeometryCollection.class.getName(), "Constructor");        
    }
    
    /**
     * Creates an empty geometry with options for GeoJsonWriter
     * @param options the {@link fr.cnes.geojson.GeoJsonWriter#options options}
     */
    public GeometryCollection(final Map<String, Object> options) {
        this();
        LOGGER.entering(GeometryCollection.class.getName(), "Constructor", options);        
        this.setOptions(options);
        LOGGER.exiting(GeometryCollection.class.getName(), "Constructor");        
    }    

    /**
     * Adds a geometry to the collection.
     * @param geometry geometry 
     */
    public void addGeometry(final Geometry geometry) {
        LOGGER.entering(GeometryCollection.class.getName(), "addGeometry", geometry.length());        
        List<Geometry> geometryCollection = (List<Geometry>) this.coordinates;
        geometryCollection.add(geometry);
        LOGGER.exiting(GeometryCollection.class.getName(), "addGeometry");        
    }

    /**
     * Sets the geometries.
     * @param geometryCollection geometry collection
     */
    public void setGeometries(final List<Geometry> geometryCollection) {
        LOGGER.entering(GeometryCollection.class.getName(), "setGeometries", geometryCollection.size());        
        this.coordinates = geometryCollection;
        LOGGER.exiting(GeometryCollection.class.getName(), "setGeometries");        
    }
    
    @Override
    public void setCoordinates(final Object coordinates) {
        LOGGER.entering(GeometryCollection.class.getName(), "setCoordinates");        
        setGeometries((List<Geometry>) coordinates);
        LOGGER.exiting(GeometryCollection.class.getName(), "setCoordinates");        
    }    

    @Override
    public List<Geometry> getCoordinates() {
        LOGGER.entering(GeometryCollection.class.getName(), "getCoordinates");        
        LOGGER.exiting(GeometryCollection.class.getName(), "getCoordinates", ((List<Geometry>) this.coordinates).size());        
        return (List<Geometry>) this.coordinates;
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
        GeometryCollection geometryCollection = (GeometryCollection) obj;
        if(!super.equals(obj)) {
            return false;
        }
        if(this.getCoordinates() == null) {
            if(geometryCollection.getCoordinates() != null) {
                return false;
            }
        } else if(!this.getCoordinates().equals(geometryCollection.getCoordinates())) {
            return false;            
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + super.hashCode();
        if (this.getCoordinates() != null) {
            hash = 79 * hash + this.getCoordinates().hashCode();
        }        
        return hash;
    }

    @Override
    public void computeBbox() {
        LOGGER.entering(GeometryCollection.class.getName(), "computeBbox");                
        List<Geometry> geoms = getCoordinates();
        double minLongValue = Double.MAX_VALUE;
        double minLatValue = Double.MAX_VALUE;
        double minAltValue = Double.MAX_VALUE;
        double maxLongValue = Double.MIN_VALUE;
        double maxLatValue = Double.MIN_VALUE;
        double maxAltValue = Double.MIN_VALUE;
        for (Geometry geom : geoms) {
            geom.computeBbox();
            double[] bbox = geom.getBbox();
            if (bbox.length == 6) {
                minLongValue = Math.max(minLongValue, bbox[0]);
                minLatValue = Math.min(minLatValue, bbox[2]);
                maxLongValue = Math.max(maxLongValue, bbox[1]);
                maxLatValue = Math.max(maxLatValue, bbox[3]);
                minAltValue = Math.min(minAltValue, bbox[4]);
                maxAltValue = Math.max(maxAltValue, bbox[5]);
            } else {
                minLongValue = Math.min(minLongValue, bbox[0]);
                minLatValue = Math.min(minLatValue, bbox[1]);
                maxLongValue = Math.max(maxLongValue, bbox[2]);
                maxLatValue = Math.max(maxLatValue, bbox[3]);
            }
        }
        if(180-Utils.EPSILON <= maxLongValue - minLongValue && maxLongValue-minLongValue <= 180+Utils.EPSILON) {
            if(minLatValue > 0 ) {
                maxLatValue = 90;
            } else {
                minLatValue = -90;
            }
        }
        double[] bbox = maxAltValue != Double.MIN_VALUE 
                ? new double[]{minLongValue, minLatValue, minAltValue, maxLongValue, maxLatValue, maxAltValue}
                : new double[]{minLongValue, minLatValue, maxLongValue, maxLatValue};
        LOGGER.exiting(GeometryCollection.class.getName(), "computeBbox", bbox);                        
        this.setBbox(bbox);
    }

    @Override
    public int length() {
        LOGGER.entering(GeometryCollection.class.getName(), "length");                        
        List<Geometry> geometryCollection = (List<Geometry>) this.coordinates;
        int length = geometryCollection == null ? 0 : geometryCollection.size();
        LOGGER.exiting(GeometryCollection.class.getName(), "length", length);                                
        return length;
    }

}
