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

    private static final String POINT = "Point";
    private static final Logger LOGGER = Logger.getLogger(Point.class.getName());
    
    /**
     * Creates a point based on the GeoJsonWriter options.
     * @param options the options
     */
    public Point(final Map<String, Object> options) {
        this();
        this.setOptions(options);
    }
    
    /**
     * Constructs a point without its coordinates.
     */
    protected Point() {
        super(POINT);
    }
    
    /**
     * Creates a point based on its position and the GeoJsonWriter options.
     * @param position position
     * @param options options
     */
    public Point(double[] position, final Map<String, Object> options) {
        this(position);
        this.setOptions(options);
    }    

    /**
     * Constructs a point from its coordinates.     
     * @param position position as longitude, latitude [,altitude]
     */
    protected Point(double[] position) {
        this();
        setPoint(position);
    }
    
    /**
     * Creates a point based on its position and the GeoJsonWriter options.
     * @param position position
     * @param options options
     */
    public Point(final Position position, final Map<String, Object> options) {
        this(position);
        this.setOptions(options);
    }    

    /**
     * Constructs a point based on Position object.    
     * @param position position of the point
     */
    protected Point(final Position position) {
        this();
        setPoint(position);
    }

    /**
     * Set the points
     *
     * @param position position
     */
    final public void setPoint(double[] position) {
        Utils.checkNotNull(position);
        this.fixLongitude(position);
        this.coordinates = position;       
    }

    /**
     * Set the point from its position
     * @param position position
     */
    final public void setPoint(Position position) {
        setPoint(position.toArray());
    }

    /**
     * Fix longitudes.
     * @param coordinates  coordinates
     */
    private void fixLongitude(double[] coordinates) {
        if (this.getOptions().containsKey(GeoJsonWriter.FIX_LONGITUDE)
                && (boolean) this.getOptions().get(GeoJsonWriter.FIX_LONGITUDE)) {
            if(coordinates[0] > 180.0) {
                coordinates[0] -= 360.0;
            }
        }
    }

    /**
     * Returns the points
     * @param <T> type
     * @param type class to convert points
     * @return the points
     */
    public <T> T getPoints(Class<T> type) {
        T result;
        switch (type.getSimpleName()) {
            case "double[]":
                result = (T) toDouble1DArray();
                break;
            case "Position":
                result = (T) toPosition();
                break;
            default:
                throw new IllegalArgumentException(type.getTypeName() + " is not allowed");
        }
        return result;
    }
    
    @Override
    public void setCoordinates(Object coordinates) {
        if (coordinates instanceof Position) {
            setPoint((Position) coordinates);            
        } else if(coordinates instanceof double[]) {
            setPoint((double[]) coordinates);            
        } else {
            throw new IllegalArgumentException("Coordinates type not found");
        }
    }    

    @Override
    public double[] getCoordinates() {
        return getPoints(double[].class);
    }

    protected double[] toDouble1DArray() {
        return (double[]) this.coordinates;
    }

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
        double[] point = (double[]) coordinates;
        this.setBbox(point.length == 3
                ? new double[]{point[0], point[1], point[2], point[0], point[1], point[2]}
                : new double[]{point[0], point[1], point[0], point[1]});
    }

    @Override
    public int length() {
        double[] point = (double[]) coordinates;
        return (point == null) ? 0 : point.length;
    }

}
