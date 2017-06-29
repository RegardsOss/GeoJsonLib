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
 * Provides a planetographic line string.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public final class LineString extends Geometry {

    private static final String LINE_STRING = "LineString";
    private static final Logger LOGGER = Logger.getLogger(LineString.class.getName());
    
    /**
     * Creates a LineString based on GeoJsonWriter options.
     * @param options the options.
     */
    public LineString(final Map<String, Object> options) {        
        this();
        LOGGER.entering(LineString.class.getName(), "Constructor", options);        
        this.setOptions(options);
        LOGGER.exiting(LineString.class.getName(), "Constructor");                
    }

    /**
     * Creates an empty LineString.
     */
    protected LineString() {
        super(LINE_STRING);
        LOGGER.entering(LineString.class.getName(), "Constructor");        
        LOGGER.exiting(LineString.class.getName(), "Constructor");                
    }
    
    /**
     * Creates a LineString based on a set of points.
     * @param points a set of points
     * @param options
     */
    public LineString(double[][] points, final Map<String, Object> options) {
        this(points);
        LOGGER.entering(LineString.class.getName(), "Constructor", options);                
        this.setOptions(options);
        LOGGER.exiting(LineString.class.getName(), "Constructor");                
    }    

    /**
     * Creates a LineString based on a set of points.
     * @param points a set of points
     */
    protected LineString(double[][] points) {
        this();
        LOGGER.entering(LineString.class.getName(), "Constructor", points);                
        setPoints(points);
        LOGGER.exiting(LineString.class.getName(), "Constructor");                
    }
    
    /**
     * Creates a LineString based on a set of Position.
     * @param points position
     * @param options
     */
    public LineString(final Position[] points, final Map<String, Object> options) {        
        this(points);
        LOGGER.entering(LineString.class.getName(), "Constructor", new Object[]{points, options});                
        this.setOptions(options);
        LOGGER.exiting(LineString.class.getName(), "Constructor");                
    }    

    /**
     * Creates a LineString based on a set of Position.
     * @param points position
     */
    protected LineString(final Position[] points) {
        this();
        LOGGER.entering(LineString.class.getName(), "Constructor", points);                
        setPoints(points);
        LOGGER.exiting(LineString.class.getName(), "Constructor");                
    }

    /**
     * Sets the points.
     * @param points the points
     */
    public void setPoints(double[][] points) {
        LOGGER.entering(LineString.class.getName(), "setPoints", points);                
        Utils.checkNotNull(points);
        fixLongitude(points);
        this.coordinates = points;        
        LOGGER.exiting(LineString.class.getName(), "setPoints");                
    }

    /**
     * Sets the points.
     * @param points the points
     */
    public void setPoints(final Position[] points) {
        LOGGER.entering(LineString.class.getName(), "setPoints", points);                        
        double[][] coord = new double[points.length][];
        int row = 0;
        for (Position point : points) {
            coord[row] = point.toArray();
            row++;
        }
        setPoints(coord);
        LOGGER.exiting(LineString.class.getName(), "setPoints");                        
    }

    /**
     * Returns the points along the LineString.
     * @param <T> Class to cast the points
     * @param type Class to cast the points.
     * @return the points in the casted class.
     */
    public <T> T getPoints(final Class<T> type) {
        LOGGER.entering(LineString.class.getName(), "getPoints", type);                        
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
        LOGGER.exiting(LineString.class.getName(), "getPoints", result);                        
        return result;
    }
    
    @Override
    public void setCoordinates(Object coordinates) {
        LOGGER.entering(LineString.class.getName(), "setCoordinates");                        
        if( coordinates instanceof Position[]) {
            setPoints((Position[]) coordinates);
        } else if (coordinates instanceof double[][]) {
            setPoints((double[][]) coordinates);
        } else {
            LOGGER.severe("coordinates type not supported");
            throw new IllegalArgumentException("coordinates type not supported");
        }        
        LOGGER.exiting(LineString.class.getName(), "setCoordinates");                        
    }    

    @Override
    public double[][] getCoordinates() {
        LOGGER.entering(LineString.class.getName(), "getCoordinates");                                
        double[][] points = getPoints(double[][].class);
        LOGGER.exiting(LineString.class.getName(), "getCoordinates", points);                        
        return points;
    }
    
    private void fixLongitude(double[][] coordinates) {
        LOGGER.entering(LineString.class.getName(), "fixLongitude", coordinates);                                
        if (this.getOptions().containsKey(GeoJsonWriter.FIX_LONGITUDE) 
                && (boolean) this.getOptions().get(GeoJsonWriter.FIX_LONGITUDE)) {
            LOGGER.fine("longitude is being fixed");                                    
            for (double[] coordinate : coordinates) {
                if (coordinate[0] > 180.0) {
                    coordinate[0] -= 360.0;
                }
            }
        }
        LOGGER.exiting(LineString.class.getName(), "fixlongitude");                                
    }    

    /**
     * Casts to 2D double array.
     * @return 2D double array
     */
    protected double[][] toDouble2DArray() {
        return (double[][]) this.coordinates;
    }

    /**
     * Casts to position array.
     * @return position array
     */
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
        LineString lineString = (LineString) obj;
        if(!super.equals(obj)) {
            return false;
        }
        if(this.getCoordinates() == null) {
            if(lineString.getCoordinates() != null) {
                return false;
            }
        } else if(!Arrays.deepEquals(this.getCoordinates(), lineString.getCoordinates())) {
            return false;            
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + super.hashCode();
        if (this.getCoordinates() != null) {
            int result = getCoordinates().length;
            for (int i = 0; i < result; i++) {
                double[] value = getCoordinates()[i];
                Point p = new Point(value);
                hash = hash * 79 + p.hashCode();
            }
        }
        return hash;
    }
    
    /**
     * Number of positions.
     * @return number of positions
     */
    public int nbPositions() {
        double[][] positions = toDouble2DArray();
        return positions.length;
    }

    @Override
    public void computeBbox() {
        LOGGER.entering(LineString.class.getName(), "computeBbox");                                
        double[][] points = toDouble2DArray();
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
             if(currentPoint.length == 3) {
                 minAltValue = Math.min(minAltValue, currentPoint[2]);
                 maxAltValue = Math.max(maxAltValue, currentPoint[2]);
             }              
            
        }
        if(180-EPSILON <= maxLongValue - minLongValue && maxLongValue-minLongValue <= 180+EPSILON) {
            if(minLatValue > 0 ) {
                maxLatValue = 90;
            } else {
                minLatValue = -90;
            }
        }        
        double[] bbox = maxAltValue != Double.MIN_VALUE 
                ? new double[]{minLongValue, minLatValue, minAltValue, maxLongValue, maxLatValue, maxAltValue} 
                : new double[]{minLongValue, minLatValue, maxLongValue, maxLatValue}; 
        LOGGER.exiting(LineString.class.getName(), "computeBbox", bbox);                                        
        this.setBbox(bbox);        
    }

    @Override
    public int length() {
        LOGGER.entering(LineString.class.getName(), "length");                                        
        double[][] points = toDouble2DArray();
        int length = (points == null) ? 0 : points.length;
        LOGGER.exiting(LineString.class.getName(), "computeBbox", length);                                        
        return length;
    }

}
 