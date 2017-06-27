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
import static fr.cnes.geojson.Utils.EPSILON;
import fr.cnes.geojson.object.Geometry;
import java.util.Arrays;

/**
 * Provides a planetographic multi line string.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class MultiLineString extends Geometry{
    
    private static final String MULTI_LINE_STRING = "MultiLineString";
        
    /**
     * Creates an empty MultiLineString.
     */
    public MultiLineString() {
        super(MULTI_LINE_STRING);
    }
    
    /**
     * Creates a MultiLineString.
     * @param type geometry type
     */
    protected MultiLineString(final String type) {
        super(type);
    }
    
    /**
     * Creates a MultiLineString based on a set of points.
     * @param points set of points
     */
    public MultiLineString(double[][][] points) {
        this();
        setPoints(points);
    }        
    
    /**
     * Creates a MultiLineString based on a set of points and a geometry type.
     * @param type geometry type
     * @param points a set of points
     */
    protected MultiLineString(final String type, double[][][] points) {
        this(type);
        setPoints(points);
    }     
    
    /**
     * Creates a MultiLineString based on a set of LineString.
     * @param lineStrings set of LineString
     */
    public MultiLineString(final LineString[] lineStrings) {
        this();
        setPoints(lineStrings);
    } 
    
    /**
     * Creates a MultiLineString based on a geometry type and a set of LineString
     * @param type geometry type
     * @param lineStrings set of LineString
     */
    protected MultiLineString(final String type, final LineString[] lineStrings) {
        this(type);
        setPoints(lineStrings);
    }      
    
    /**
     * Sets the points
     * @param points set of points
     * @throws IllegalArgumentException Will throw an exception when the object is null
     */
    public void setPoints(double[][][] points) {
        Utils.checkNotNull(points);
        this.coordinates = points;
    }
    
    /**
     * Sets a set of LineString
     * @param lineStrings set of LineString
     * @throws IllegalArgumentException Will throw an exception when the object is null     
     */
    public void setPoints(final LineString[] lineStrings) {        
        Utils.checkNotNull(lineStrings);
        double[][][] points = new double[lineStrings.length][][];        
        int indexLineString=0;
        for (LineString lineString:lineStrings) {
            points[indexLineString] = lineString.toDouble2DArray();
            indexLineString++;
        }       
        this.coordinates = points;
    }    
    
    /**
     * Returns the points as a double[][][] or LineString[].
     * @param <T> Object
     * @param type class to cast
     * @return the points as a double[][][] or LineString[]
     */
    public <T> T getPoints(Class<T> type) {
        T result;
        switch(type.getSimpleName()) {
            case "double[][][]" :
                result = (T) toDouble3DArray();
                break;
            case "LineString[]" :
                result = (T) toLineStringArray();
                break;
            default:
                throw new IllegalArgumentException(type.getTypeName()+" is not allowed");               
        }
        return result;
    }
    
    @Override
    public double[][][] getCoordinates() {
        return getPoints(double[][][].class);
    }
    
    /**
     * Converts to 3D double array.
     * @return 3D double array
     */
    protected double[][][] toDouble3DArray() {        
        return (double[][][]) this.coordinates;
    }    
    
    /**
     * Converts to an array of LineString.
     * @return array of LineString
     */
    protected LineString[] toLineStringArray() {
        double[][][] points = toDouble3DArray();
        int nbLineString = points.length;
        LineString[] lineStrings = new LineString[nbLineString];
        for(int currentLineString=0; currentLineString<nbLineString ; currentLineString++) {
            lineStrings[currentLineString] = new LineString(points[currentLineString]);
        }
        return lineStrings;
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
        MultiLineString multiLineString = (MultiLineString) obj;
        if(!super.equals(obj)) {
            return false;
        }
        if(this.getCoordinates() == null) {
            if(multiLineString.getCoordinates() != null) {
                return false;
            }
        } else if(!Arrays.deepEquals(this.getCoordinates(), multiLineString.getCoordinates())) {
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
                double[][] value = getCoordinates()[i];
                LineString ls = new LineString(value);
                hash = hash * 59 + ls.hashCode();
            }
        }
        return hash;
    }

    @Override
    public void setCoordinates(Object coordinates) {
        setPoints((double[][][]) coordinates);
    }

    @Override
    public void computeBbox() {
        double[][][] lineStrings = toDouble3DArray();
        int nbLineStrings = lineStrings.length;
        double minLongValue = Double.MAX_VALUE;
        double minLatValue = Double.MAX_VALUE;
        double minAltValue = Double.MAX_VALUE;
        double maxLongValue = Double.MIN_VALUE;
        double maxLatValue = Double.MIN_VALUE;
        double maxAltValue = Double.MIN_VALUE;
        for(int indexLineString=0; indexLineString<nbLineStrings ; indexLineString++) {
            double[][] currentLineString = lineStrings[indexLineString];
            LineString lineString = new LineString(currentLineString);
            lineString.computeBbox();
            double[] bbox = lineString.getBbox();          
            if(bbox.length == 6) {
                minLongValue = Math.min(minLongValue, bbox[0]);
                minLatValue = Math.min(minLatValue, bbox[1]);            
                maxLongValue = Math.max(maxLongValue, bbox[3]);
                maxLatValue = Math.max(maxLatValue, bbox[4]);                  
                minAltValue = Math.min(minAltValue, bbox[2]);
                maxAltValue = Math.max(maxAltValue, bbox[5]);                   
            } else {
                minLongValue = Math.min(minLongValue, bbox[0]);
                minLatValue = Math.min(minLatValue, bbox[1]);            
                maxLongValue = Math.max(maxLongValue, bbox[2]);
                maxLatValue = Math.max(maxLatValue, bbox[3]);                  
            }
        }
        if(180-EPSILON <= maxLongValue - minLongValue && maxLongValue-minLongValue <= 180+EPSILON) {
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
        double[][][] points = toDouble3DArray();
        return points == null ? 0 : points.length;
    }
    
}
