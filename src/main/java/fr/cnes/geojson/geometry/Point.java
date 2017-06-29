/**
 * ****************************************************************************
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
 * ****************************************************************************
 */
package fr.cnes.geojson.geometry;

import fr.cnes.geojson.GeoJsonWriter;
import fr.cnes.geojson.Utils;
import fr.cnes.geojson.object.Geometry;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Provides a planetographic point.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class Point extends Geometry {

    /**
     * Type of the geometry {@value}.
     */
    private static final String POINT = "Point";
    
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Point.class.getName());
    
    /**
     * Creates a point with options coming from the GeoJsonWriter object.
     * @param options the options
     * @see fr.cnes.geojson.AbstractGeoJsonUtility#options
     */
    public Point(final Map<String, Object> options) {
        this();
        LOGGER.entering(Point.class.getName(), "Constructor", options);
        this.setOptions(options);
        LOGGER.exiting(Point.class.getName(), "Constructor");
        
    }
    
    /**
     * Constructs an empty point.
     */
    protected Point() {
        super(POINT);
        LOGGER.entering(Point.class.getName(), "Constructor");
        LOGGER.exiting(Point.class.getName(), "Constructor");        
    }
    
    /**
     * Creates a point with its position and the GeoJsonWriter options.
     * @param position position
     * @param options options
     * @see fr.cnes.geojson.AbstractGeoJsonUtility#options
     */
    public Point(double[] position, final Map<String, Object> options) {
        this(position);
        LOGGER.entering(Point.class.getName(), "Constructor", new Object[]{position, options});        
        this.setOptions(options);
        LOGGER.entering(Point.class.getName(), "Constructor");        
    }    

    /**
     * Constructs a point with its coordinates.     
     * @param position position as longitude, latitude [,altitude]
     */
    protected Point(double[] position) {
        this();
        LOGGER.entering(Point.class.getName(), "Constructor", position);        
        setPoint(position);
        LOGGER.exiting(Point.class.getName(), "Constructor");        
    }
    
    /**
     * Creates a point with its position and the GeoJsonWriter options.
     * @param position position
     * @param options options
     * @see fr.cnes.geojson.AbstractGeoJsonUtility#options
     */
    public Point(final Position position, final Map<String, Object> options) {
        this(position);
        LOGGER.entering(Point.class.getName(), "Constructor", new Object[]{position, options});        
        this.setOptions(options);
        LOGGER.exiting(Point.class.getName(), "Constructor");        
    }    

    /**
     * Constructs a point with the Position object.    
     * @param position position of the point
     */
    protected Point(final Position position) {
        this();
        LOGGER.entering(Point.class.getName(), "Constructor", position);        
        setPoint(position);
        LOGGER.exiting(Point.class.getName(), "Constructor");        
    }

    /**
     * Set the points as an array of double.     
     * The longitude is fixed is the options is enabled {@link fr.cnes.geojson.GeoJsonWriter#FIX_LONGITUDE}
     * @param position position with longitude, latitude [,altitude]  
     * @see fr.cnes.geojson.AbstractGeoJsonUtility#options
     */
    final public void setPoint(double[] position) {
        LOGGER.entering(Point.class.getName(), "setPoints", position);        
        Utils.checkNotNull(position);
        this.fixLongitude(position);
        this.coordinates = position;       
        LOGGER.exiting(Point.class.getName(), "setPoints");        
    }

    /**
     * Set the point from its position
     * @param position position
     */
    final public void setPoint(Position position) {
        LOGGER.entering(Point.class.getName(), "setPoint", position);        
        setPoint(position.toArray());
        LOGGER.exiting(Point.class.getName(), "setPoint");        
    }

    /**
     * Fix longitudes.
     * @param coordinates  coordinates with the fixed longitudes
     */
    private void fixLongitude(double[] coordinates) {
        LOGGER.entering(Point.class.getName(), "fixLongitude", coordinates);        
        if (this.getOptions().containsKey(GeoJsonWriter.FIX_LONGITUDE)
                && (boolean) this.getOptions().get(GeoJsonWriter.FIX_LONGITUDE)) {
            LOGGER.fine("longitude is being fixed");
            if(coordinates[0] > 180.0) {
                coordinates[0] -= 360.0;
            }
        }
        LOGGER.exiting(Point.class.getName(), "fixLongitude", coordinates);                
    }

    /**
     * Returns the points
     * @param <T> type (double[] or Position)
     * @param type class to convert points (double[] or Position[])
     * @return the points
     * @throws IllegalArgumentException An exception is thrown when the class to
     * convert is not supported
     */ 
    public <T> T getPoints(Class<T> type) throws IllegalArgumentException {
        T result;
        switch (type.getSimpleName()) {
            case "double[]":
                result = (T) toDouble1DArray();
                break;
            case "Position":
                result = (T) toPosition();
                break;
            default:
                LOGGER.severe(String.format("%s is not allowed", type.getTypeName()));
                throw new IllegalArgumentException(type.getTypeName() + " is not allowed");
        }
        return result;
    }
    
    /**
     * Sets the coordinates as a Position or an array of double
     * @param coordinates coordinates
     * @throws IllegalArgumentException An exception is thrown when the coordinate type
     * is not supported
     */
    @Override
    public void setCoordinates(Object coordinates) throws IllegalArgumentException {
        if (coordinates instanceof Position) {
            setPoint((Position) coordinates);            
        } else if(coordinates instanceof double[]) {
            setPoint((double[]) coordinates);            
        } else {
            LOGGER.severe("Coordinates type not found");
            throw new IllegalArgumentException("Coordinates type not found");
        }
    }    

    @Override
    public double[] getCoordinates() {
        return getPoints(double[].class);
    }

    /**
     * Returns the coordinates as an array of double.
     * @return the coordinates as an array of double
     */
    protected double[] toDouble1DArray() {
        return (double[]) this.coordinates;
    }

    /**
     * Returns the coordinates as a Position
     * @return the coordinates as a Position
     */
    protected Position toPosition() {
        return new Position((double[]) this.coordinates);
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
        Point point = (Point) obj;
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getCoordinates() == null) {
            if (point.getCoordinates() != null) {
                return false;
            }
        } else if (!Arrays.equals(this.getCoordinates(), point.getCoordinates())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 39 * hash + super.hashCode();
        if (this.getCoordinates() != null) {
            int result = getCoordinates().length;
            for (int i = 0; i < result; i++) {
                double value = getCoordinates()[i];
                hash = hash * 39 + (int) (Double.doubleToLongBits(value) ^ (Double.doubleToLongBits(value) >>> 32));
            }
        }
        return hash;
    }

    @Override
    public void computeBbox() {
        LOGGER.entering(Point.class.getName(), "computeBbox");                
        double[] point = (double[]) coordinates;
        double[] bbox = point.length == 3
                ? new double[]{point[0], point[1], point[2], point[0], point[1], point[2]}
                : new double[]{point[0], point[1], point[0], point[1]};         
        this.setBbox(bbox);
        LOGGER.exiting(Point.class.getName(), "fixLongitude", bbox);                
    }

    /**
     * Returns the number of coordinates in a point.
     * @return the number of coordinates in a point
     */
    @Override
    public int length() {
        double[] point = (double[]) coordinates;
        return (point == null) ? 0 : point.length;
    }

}
