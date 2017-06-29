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

import fr.cnes.geojson.GeoJsonWriter;
import fr.cnes.geojson.Utils;
import static fr.cnes.geojson.Utils.EPSILON;
import fr.cnes.geojson.object.Geometry;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Provides a planetographic multi points.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public final class MultiPoint extends Geometry {

    private static final String MULTI_POINT = "MultiPoint";
    private static final Logger LOGGER = Logger.getLogger(MultiPoint.class.getName());
    
    /**
     * Creates MultiPoint based on GeoJsonWriter options.
     * @param options the options
     */
    public MultiPoint(final Map<String,Object> options) {
        this();
        LOGGER.entering(MultiPoint.class.getName(), "Constructor", options);                                                                
        this.setOptions(options);
        LOGGER.exiting(MultiPoint.class.getName(), "Constructor");                                                                        
    }
    
    /**
     * Creates an empty MultiPoint.
     */
    protected MultiPoint() {
        super(MULTI_POINT);
        LOGGER.entering(MultiPoint.class.getName(), "Constructor");                                                                
        LOGGER.exiting(MultiPoint.class.getName(), "Constructor");                                                                        
    }
    
    public MultiPoint(double[][] points, final Map<String,Object> options) {
        this(points);
        LOGGER.entering(MultiPoint.class.getName(), "Constructor", options);                                                                        
        this.setOptions(options);
        LOGGER.exiting(MultiPoint.class.getName(), "Constructor");                                                                        
    }    

    /**
     * Creates a MultiPoint based on a set of points.     
     * @param points set of points
     */
    protected MultiPoint(double[][] points) {
        this();
        LOGGER.entering(MultiPoint.class.getName(), "Constructor", points);                                                                        
        setPoints(points);
        LOGGER.exiting(MultiPoint.class.getName(), "Constructor");                                                                        
    }
    
    /**
     * Creates a MultiPoint based on a set of position and GeoJsonWriter options.
     * @param points positions
     * @param options GeoJsonWriter options
     */
    public MultiPoint(final Position[] points, final Map<String,Object> options) {
        this(points);
        LOGGER.entering(MultiPoint.class.getName(), "Constructor", new Object[]{points, options});                                                                        
        this.setOptions(options);
        LOGGER.exiting(MultiPoint.class.getName(), "Constructor");                                                                        
    }    

    /**
     * Creates a MultiPoint based on a set of Position.     
     * @param points set of Position
     */
    protected MultiPoint(final Position[] points) {
        this();
        LOGGER.entering(MultiPoint.class.getName(), "Constructor", points);                                                                        
        setPoints(points);
        LOGGER.exiting(MultiPoint.class.getName(), "Constructor");                                                                        
    }

    /**
     * Sets the points
     *
     * @param points the points as a 2D double array
     */
    public void setPoints(double[][] points) {
        LOGGER.entering(MultiPoint.class.getName(), "setPoints", points);                                                                        
        Utils.checkNotNull(points);
        fixLongitude(points);
        this.coordinates = points;
        LOGGER.exiting(MultiPoint.class.getName(), "setPoints");                                                                        
    }
    
    private void fixLongitude(double[][] coordinates) {
        LOGGER.entering(MultiPoint.class.getName(), "fixLongitude", coordinates);                                                                        
        if (this.getOptions().containsKey(GeoJsonWriter.FIX_LONGITUDE)
                && (boolean) this.getOptions().get(GeoJsonWriter.FIX_LONGITUDE)) {
            LOGGER.fine("Fix longitude is beeing processed");
            for (double[] coordinate : coordinates) {
                if (coordinate[0] > 180.0) {
                    coordinate[0] -= 360.0;
                }
            }
        }
        LOGGER.exiting(MultiPoint.class.getName(), "fixLongitude");                                                                        
    }    

    /**
     * Sets the points     
     * @param points the points as a Position array
     */
    public void setPoints(final Position[] points) {
        LOGGER.entering(MultiPoint.class.getName(), "setPoints", points);                                                                                
        Utils.checkNotNull(points);
        int nbPoints = points.length;
        double[][] coord = new double[nbPoints][];
        int currentIndexPoint = 0;
        for (Position point : points) {
            coord[currentIndexPoint] = point.toArray();
            currentIndexPoint++;
        }
        setPoints(coord);
        LOGGER.exiting(MultiPoint.class.getName(), "setPoints");                                                                                
    }

    /**
     * Returns the points as double[][] or Position[].
     *
     * @param <T> type as double[][] or Position[]
     * @param type class to casr
     * @return the points
     */
    public <T> T getPoints(Class<T> type) {
        LOGGER.entering(MultiPoint.class.getName(), "setPoints", type);                                                                                
        T result;
        switch (type.getSimpleName()) {
            case "double[][]":
                result = (T) toDouble2DArray();
                break;
            case "Position[]":
                result = (T) toPositionArray();
                break;
            default:
                throw new IllegalArgumentException(type.getTypeName() + " is not allowed");
        }
        LOGGER.exiting(MultiPoint.class.getName(), "setPoints", result);                                                                                
        return result;
    }
    
    @Override
    public void setCoordinates(Object coordinates) {
        LOGGER.entering(MultiPoint.class.getName(), "setCoordinates");                                                                                
        if(coordinates instanceof Position[]) {
            setPoints((Position[]) coordinates);            
        } else if(coordinates instanceof double[][]) {
            setPoints((double[][]) coordinates);
        } else {
            LOGGER.severe("Coordinates type not supported");                                                                                            
            throw new IllegalArgumentException("Coordinates type not supported");
        }
        LOGGER.exiting(MultiPoint.class.getName(), "setCoordinates");                                                                                        
    }
    

    @Override
    public double[][] getCoordinates() {
        return getPoints(double[][].class);
    }

    protected double[][] toDouble2DArray() {
        return (double[][]) this.coordinates;
    }

    protected Position[] toPositionArray() {
        double[][] points = (double[][]) this.coordinates;
        int nbRows = points.length;
        Position[] positions = new Position[nbRows];
        for (int row = 0; row < nbRows; row++) {
            positions[row] = new Position(points[row]);
        }
        return positions;
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
        MultiPoint multiPoint = (MultiPoint) obj;
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getCoordinates() == null) {
            if (multiPoint.getCoordinates() != null) {
                return false;
            }
        } else if (!Arrays.deepEquals(this.getCoordinates(), multiPoint.getCoordinates())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + super.hashCode();
        if (this.getCoordinates() != null) {
            int result = getCoordinates().length;
            for (int i = 0; i < result; i++) {
                double[] value = getCoordinates()[i];
                Point p = new Point(value);
                hash = hash * 59 + p.hashCode();
            }
        }
        return hash;
    }

    @Override
    public void computeBbox() {
        LOGGER.entering(MultiPoint.class.getName(), "computeBbox");                                                                                                
        double[][] points = ((double[][]) this.coordinates);
        int nbPoints = points.length;
        double minLongValue = Double.MAX_VALUE;
        double minLatValue = Double.MAX_VALUE;
        double minAltValue = Double.MAX_VALUE;
        double maxLongValue = Double.MIN_VALUE;
        double maxLatValue = Double.MIN_VALUE;
        double maxAltValue = Double.MIN_VALUE;
        for (int indexPoint = 0; indexPoint < nbPoints; indexPoint++) {
            double[] currentPoint = points[indexPoint];
            minLongValue = Math.min(minLongValue, currentPoint[0]);
            minLatValue = Math.min(minLatValue, currentPoint[1]);
            maxLongValue = Math.max(maxLongValue, currentPoint[0]);
            maxLatValue = Math.max(maxLatValue, currentPoint[1]);
            if (currentPoint.length == 3) {
                minAltValue = Math.min(minAltValue, currentPoint[2]);
                maxAltValue = Math.max(maxAltValue, currentPoint[2]);
            }
        }
        if (180 - EPSILON <= maxLongValue - minLongValue && maxLongValue - minLongValue <= 180 + EPSILON) {
            if (minLatValue > 0) {
                maxLatValue = 90;
            } else {
                minLatValue = -90;
            }
        }
        double[] bbox = maxAltValue != Double.MIN_VALUE
                ? new double[]{minLongValue, minLatValue, minAltValue, maxLongValue, maxLatValue, maxAltValue}
                : new double[]{minLongValue, minLatValue, maxLongValue, maxLatValue};
        LOGGER.exiting(MultiPoint.class.getName(), "computeBbox", bbox);                                                                                                
        this.setBbox(bbox);
    }

    @Override
    public int length() {
        LOGGER.entering(MultiPoint.class.getName(), "length");                                                                                                                
        double[][] points = toDouble2DArray();
        int length = (points == null) ? 0 : points.length;
        LOGGER.exiting(MultiPoint.class.getName(), "length", length); 
        return length;
    }

}
