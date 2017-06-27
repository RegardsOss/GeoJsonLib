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

import fr.cnes.geojson.object.Geometry;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a list of geometries.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class GeometryCollection extends Geometry {

    private static final String GEOMETRY_COLLECTION_STRING = "GeometryCollection";

    /**
     * Creates an empty geometry collection.
     */
    public GeometryCollection() {
        super(GEOMETRY_COLLECTION_STRING);
        this.coordinates = new ArrayList<>();
    }

    /**
     * Add a geometry.
     * @param geometry geometry 
     */
    public void addGeometry(final Geometry geometry) {
        List<Geometry> geometryCollection = (List<Geometry>) this.coordinates;
        geometryCollection.add(geometry);
    }

    /**
     * Sets the geometries.
     * @param geometryCollection geometry collection
     */
    public void setGeometries(final List<Geometry> geometryCollection) {
        this.coordinates = geometryCollection;
    }

    @Override
    public List<Geometry> getCoordinates() {
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
    public void setCoordinates(final Object coordinates) {
        setGeometries((List<Geometry>) coordinates);
    }

    @Override
    public void computeBbox() {
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
        if(180-0.00001 <= maxLongValue - minLongValue && maxLongValue-minLongValue <= 180+0.00001) {
            if(minLatValue > 0 ) {
                maxLatValue = 90;
            } else {
                minLatValue = -90;
            }
        }
        this.setBbox(maxAltValue != Double.MIN_VALUE
                ? new double[]{minLongValue, minLatValue, minAltValue, maxLongValue, maxLatValue, maxAltValue}
                : new double[]{minLongValue, minLatValue, maxLongValue, maxLatValue});
    }

    @Override
    public int length() {
        List<Geometry> geometryCollection = (List<Geometry>) this.coordinates;
        return geometryCollection == null ? 0 : geometryCollection.size();
    }

}
