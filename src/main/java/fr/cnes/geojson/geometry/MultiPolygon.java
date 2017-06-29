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
import static fr.cnes.geojson.Utils.EPSILON;
import fr.cnes.geojson.object.Geometry;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Provides a multi polygons.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class MultiPolygon extends Geometry {

    private static final String MULTI_POLYGON = "MultiPolygon";
    private static final Logger LOGGER = Logger.getLogger(MultiPolygon.class.getName());
    
    /**
     * Creates a MultiPolygon based on the GeoJsonWriter options.
     * @param options the options
     */
    public MultiPolygon(final Map<String, Object> options) {
        this();
        LOGGER.entering(MultiPolygon.class.getName(), "Constructor", options);
        this.setOptions(options);
        LOGGER.exiting(MultiPolygon.class.getName(), "Constructor");        
    }

    /**
     * Creates an empty MultiPolygon.
     */
    protected MultiPolygon() {
        super(MULTI_POLYGON);
        LOGGER.entering(MultiPolygon.class.getName(), "Constructor");
        LOGGER.exiting(MultiPolygon.class.getName(), "Constructor");        
    }
    
    /**
     * Creates a MultiPolygon based on coordinates and GeoJsonWriter options
     * @param points the coordinates
     * @param options the options
     */
    public MultiPolygon(double[][][][] points, final Map<String, Object> options) {
        this(points);
        LOGGER.entering(MultiPolygon.class.getName(), "Constructor", new Object[]{points, options});        
        this.setOptions(options);
        LOGGER.exiting(MultiPolygon.class.getName(), "Constructor");        
    }    

    /**
     * Creates a MultiPolygon based on coordinates.
     * @param points the coordinates
     */
    protected MultiPolygon(double[][][][] points) {
        this();
        LOGGER.entering(MultiPolygon.class.getName(), "Constructor", points);                
        setPoints(points);
        LOGGER.exiting(MultiPolygon.class.getName(), "Constructor");                
    }
    
    /**
     * Creates a MultiPolygon based on a set of polygons and GeoJsonWriter options.
     * @param polygons the polygons
     * @param options the options
     */
    public MultiPolygon(final Polygon[] polygons, final Map<String, Object> options) {
        this(polygons);
        LOGGER.entering(MultiPolygon.class.getName(), "Constructor", new Object[]{polygons, options});                        
        this.setOptions(options);
        LOGGER.exiting(MultiPolygon.class.getName(), "Constructor");                        
    }     

    /**
     * Creates a MultiPolygon based on a set of polygons.
     * @param polygons polygons
     */
    protected MultiPolygon(final Polygon[] polygons) {
        this();
        LOGGER.entering(MultiPolygon.class.getName(), "Constructor", polygons);                        
        setPoints(polygons);
        LOGGER.exiting(MultiPolygon.class.getName(), "Constructor");                        
    }

    /**
     * Sets the number of points.
     * @param points points
     */
    public final void setPoints(double[][][][] points) {
        LOGGER.entering(MultiPolygon.class.getName(), "setPoints", points);                        
        fixLongitude(points);
        this.coordinates = points;
        LOGGER.exiting(MultiPolygon.class.getName(), "setPoints");                        
    }

    /**
     * Sets the numner of points based on an array of polygons.
     * @param polygons polygons
     */
    public final void setPoints(final Polygon[] polygons) {
        LOGGER.entering(MultiPolygon.class.getName(), "setPoints", polygons);                        
        double[][][][] points = new double[polygons.length][][][];
        int indexPolygon = 0;
        for (Polygon polygon : polygons) {
            points[indexPolygon] = polygon.toDouble3DArray();
            indexPolygon++;
        }
        setPoints(points);
        LOGGER.exiting(MultiPolygon.class.getName(), "setPoints");                        
    }
    
    /**
     * Fix longitude of multi polygons
     * @param multiPolygons multi polygons
     */
    private void fixLongitude(double[][][][] multiPolygons) {
        LOGGER.entering(MultiPolygon.class.getName(), "fixLongitude", multiPolygons);                        
        if (this.getOptions().containsKey(GeoJsonWriter.FIX_LONGITUDE)
                && (boolean) this.getOptions().get(GeoJsonWriter.FIX_LONGITUDE)) {
            LOGGER.fine("Fix longitude is being processed");
            for (double[][][] polygon : multiPolygons) {
                for (double[][] line : polygon) {
                    for (double[] coordinate : line) {
                        if (coordinate[0] > 180.0) {
                            coordinate[0] -= 360.0;
                        }
                    }                
                }              
            }
        }
        LOGGER.exiting(MultiPolygon.class.getName(), "fixLongitude");                                
    }      

    /**
     * Returns points
     * @param <T> type
     * @param type class to cast the points
     * @return an array of double of several dimensions.
     */
    public <T> T getPoints(Class<T> type) {
        LOGGER.entering(MultiPolygon.class.getName(), "getPoints", type);                                
        T result;
        switch (type.getSimpleName()) {
            case "double[][][][]":
                result = (T) toDouble4DArray();
                break;
            case "Polygon[]":
                result = (T) toPolygonArray();
                break;
            default:
                LOGGER.severe(String.format("%s is not allowed", type.getTypeName()));
                throw new IllegalArgumentException(type.getTypeName() + " is not allowed");
        }
        LOGGER.exiting(MultiPolygon.class.getName(), "getPoints", result);                                
        return result;
    }
    
    @Override
    public void setCoordinates(Object coordinates) {
        if(coordinates instanceof Polygon[]) {
            setPoints((Polygon[]) coordinates);
        } else if(coordinates instanceof double[][][][]) {
            setPoints((double[][][][]) coordinates);            
        } else {
           LOGGER.severe("Coordinates type not supported");            
           throw new IllegalArgumentException("Coordinates type not supported");
        }
    }    

    @Override
    public double[][][][] getCoordinates() {
        return getPoints(double[][][][].class);
    }

    protected double[][][][] toDouble4DArray() {
        return (double[][][][]) this.coordinates;
    }

    protected Polygon[] toPolygonArray() {
        double[][][][] points = (double[][][][]) this.coordinates;
        int nbPolygons = points.length;
        Polygon[] polygons = new Polygon[nbPolygons];
        for (int currentPolygon = 0; currentPolygon < nbPolygons; currentPolygon++) {
            polygons[currentPolygon] = new Polygon(points[currentPolygon]);
        }
        return polygons;
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
        MultiPolygon multiPolygon = (MultiPolygon) obj;
        if(!super.equals(obj)) {
            return false;
        }
        if(this.getCoordinates() == null) {
            if(multiPolygon.getCoordinates() != null) {
                return false;
            }
        } else if(!Arrays.deepEquals(this.getCoordinates(), multiPolygon.getCoordinates())) {
            return false;            
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 99;
        hash = 99 * hash + super.hashCode();
        if (this.getCoordinates() != null) {
            int result = getCoordinates().length;
            for (int i = 0; i < result; i++) {
                double[][][] value = getCoordinates()[i];
                Polygon poly = new Polygon(value);
                hash = hash * 99 + poly.hashCode();
            }
        }
        return hash;
    }

    @Override
    public void computeBbox() {
        LOGGER.entering(MultiPolygon.class.getName(), "computeBbox");                                        
        double[][][][] multiPolys = (double[][][][]) this.coordinates;
        int nbMultiPolys = multiPolys.length;
        double minLongValue = Double.MAX_VALUE;
        double minLatValue = Double.MAX_VALUE;
        double minAltValue = Double.MAX_VALUE;
        double maxLongValue = Double.MIN_VALUE;
        double maxLatValue = Double.MIN_VALUE;
        double maxAltValue = Double.MIN_VALUE;
        for (int indexPoly = 0; indexPoly < nbMultiPolys; indexPoly++) {
            double[][][] currentPoly = multiPolys[indexPoly];
            Polygon poly = new Polygon(currentPoly);
            poly.computeBbox();
            double[] bbox = poly.getBbox();           
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
        double[] bbox = maxAltValue != Double.MIN_VALUE 
                ? new double[]{minLongValue, minLatValue, minAltValue, maxLongValue, maxLatValue, maxAltValue}
                : new double[]{minLongValue, minLatValue, maxLongValue, maxLatValue};
        this.setBbox(bbox);          
        LOGGER.exiting(MultiPolygon.class.getName(), "computeBbox", bbox);                                                      
    }

    /**
     * Returns the number of polygons.
     * @return 
     */
    @Override
    public int length() {
        LOGGER.entering(MultiPolygon.class.getName(), "length");                                        
        double[][][][] points = toDouble4DArray();
        int length = (points == null) ? 0 : points.length;
        LOGGER.exiting(MultiPolygon.class.getName(), "length", length);                                                
        return length;
    }
}
