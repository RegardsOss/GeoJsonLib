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

import static fr.cnes.geojson.Utils.EPSILON;
import fr.cnes.geojson.object.Geometry;
import java.util.Arrays;

/**
 * Provides a multi polygons.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class MultiPolygon extends Geometry {

    private static final String MULTI_POLYGON = "MultiPolygon";

    public MultiPolygon() {
        super(MULTI_POLYGON);
    }

    public MultiPolygon(double[][][][] points) {
        this();
        setPoints(points);
    }

    public MultiPolygon(Polygon[] polygons) {
        this();
        setPoints(polygons);
    }

    public final void setPoints(double[][][][] points) {
        this.coordinates = points;
    }

    public final void setPoints(Polygon[] polygons) {
        double[][][][] points = new double[polygons.length][][][];
        int indexPolygon = 0;
        for (Polygon polygon : polygons) {
            points[indexPolygon] = polygon.toDouble3DArray();
            indexPolygon++;
        }
        this.coordinates = points;
    }

    public <T> T getPoints(Class<T> type) {
        T result;
        switch (type.getSimpleName()) {
            case "double[][][][]":
                result = (T) toDouble4DArray();
                break;
            case "Polygon[]":
                result = (T) toPolygonArray();
                break;
            default:
                throw new IllegalArgumentException(type.getTypeName() + " is not allowed");
        }
        return result;
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
    public void setCoordinates(Object coordinates) {
        setPoints((double[][][][]) coordinates);
    }

    @Override
    public void computeBbox() {
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
        this.setBbox(maxAltValue != Double.MIN_VALUE 
                ? new double[]{minLongValue, minLatValue, minAltValue, maxLongValue, maxLatValue, maxAltValue}
                : new double[]{minLongValue, minLatValue, maxLongValue, maxLatValue});          
    }

    @Override
    public int length() {
        double[][][][] points = toDouble4DArray();
        return points == null ? 0 : points.length;
    }
}
