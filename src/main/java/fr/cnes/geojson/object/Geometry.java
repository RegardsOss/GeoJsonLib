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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Geometry object represents points, curves, and surfaces in
 * coordinate space.  
 * 
 * Every Geometry object is a GeoJSON object no matter where it occurs in a 
 * GeoJSON text.
 * <ul>
 * <li>The value of a Geometry object's "type" member MUST be one of the
 * seven geometry types</li>
 * <li>A GeoJSON Geometry object of any type other than
 * "GeometryCollection" has a member with the name "coordinates".
 * The value of the "coordinates" member is an array.  The structure
 * of the elements in this array is determined by the type of
 * geometry.  GeoJSON processors MAY interpret Geometry objects with
 * empty "coordinates" arrays as null objects.</li> 
 * </ul>
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 * @param <T>
 */
public abstract class Geometry<T extends Geometry> extends GeoJsonObject{
    
    /**
     * coordinates of the geometry.
     */
    protected Object coordinates;
        
    /**
     * Empty geometry.
     * @param type geometry type
     */
    public Geometry(final String type) {
        super(type);
    }    
    
    /**
     * Creates an empty geometry.
     * @param <T> Geometry
     * @param clazz Class to create
     * @return the geometry
     */
    public static <T extends Geometry> T createGeometry(final Class clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Geometry.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    } 

    /**
     * Returns the geometry.
     * @param <T> geometry
     * @return the geometry
     */
    public <T extends Geometry> T getShape() {
        return (T) this;
    }
    
    /**
     * Returns the length of the first dimension of the array.
     * @return the number of elements
     */
    public abstract int length();
    
    /**
     * Returns the coordinates.
     * @return the coordinates
     */
    public abstract Object getCoordinates();
    
    /**
     * Sets the coordinates.
     * @param coordinates the coordinates
     */
    public abstract void setCoordinates(final Object coordinates);
    
    /**
     * Computes the Bbox.
     */
    public abstract void computeBbox();   
             
}
