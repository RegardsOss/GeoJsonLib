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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Provides a planetographic polygon. A polygon uses the concept of a linear
 * ring:
 * <ul>
 * <li>A linear ring is a closed LineString with four or more positions.</li>
 * <li>The first and last positions are equivalent, and they MUST contain
 * identical values; their representation SHOULD also be identical.</li>
 * <li>A linear ring is the boundary of a surface or the boundary of a hole in a
 * surface.</li>
 * <li>A linear ring MUST follow the right-hand rule with respect to the area it
 * bounds, i.e., exterior rings are counterclockwise, and holes are
 * clockwise.</li>
 * </ul>
 * the "coordinates" member MUST be an array of linear ring coordinate arrays.
 * For Polygons with more than one of these rings, the first MUST be the
 * exterior ring, and any others MUST be interior rings. The exterior ring
 * bounds the surface, and the interior rings (if present) bound holes within
 * the surface.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public final class Polygon extends MultiLineString {

    private static final String POLYGON = "Polygon";
    private static final Logger LOGGER = Logger.getLogger(Polygon.class.getName());

    /**
     * Minimum number of points in a polygon (3+the first one because is closed)
     */
    private static final int NB_POINTS = 4;
    
    public Polygon(final Map<String, Object> options) {
        this();   
        this.setOptions(options);
    }

    /**
     * Creates a polygon without points.
     */
    protected Polygon() {
        super(POLYGON);
    }
    
    /**
     * Creates a polygon based on rings and GeoJsonWriter options.
     * @param exteriorRing exterior rings
     * @param interiorRings holes
     * @param options options
     */
    public Polygon(final LineString[] exteriorRing, final List<LineString[]> interiorRings, final Map<String, Object> options) {
        this(exteriorRing, interiorRings);   
        this.setOptions(options);
    }    

    /**
     * Creates a polygon based on a one exterior ring and holes.
     *
     * @param exteriorRing exterior ring
     * @param interiorRings holes
     */
    protected Polygon(final LineString[] exteriorRing, List<LineString[]> interiorRings) {
        super(POLYGON);
        setPoints(exteriorRing, interiorRings);
    }

    /**
     * Creates a polygon based on points and GeoJsonWriter options
     * @param points points
     * @param options options
     */
    public Polygon(double[][][] points, final Map<String, Object> options) {
        this(points);   
        this.setOptions(options);
    }    
    
    /**
     * Creates a polygon based on a set of points. 
     * The first element of the array is the exterior ring. The other ones are 
     * the interior rings.     
     * @param points points
     */
    protected Polygon(double[][][] points) {
        super(POLYGON);
        setPoints(points);
    }

    /**
     * Check whether the polygon is a valid polygon.     
     * @param points points
     * @throws IllegalArgumentException Will be thrown when the polygon is not
     * valid
     */
    private void checkPolygon(double[][][] points) {
        StringBuilder errorMessage = new StringBuilder();
        if (!isValidPolygon(points, errorMessage)) {
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }

    /**
     * Checks whether the polygon is valid :
     * <ul>
     * <li>Checks the number of points</li>
     * <li>Checks whether the polygon is closed</li>
     * <li>Checks the exterior ring is counter clockwised and the interior rings
     * are counter clocwised.
     *
     * @param points points
     * @param message error message
     * @return True the polygon is valid otherwise False
     */
    private boolean isValidPolygon(double[][][] points, StringBuilder message) {
        boolean result = true;
        int nbRings = points.length;
        for (int indexRing = 0; indexRing < nbRings; indexRing++) {
            double[][] ring = points[indexRing];
            result = checkValidNumberPoint(indexRing + 1, ring, message, result);
            result = checkClosedPolygon(indexRing + 1, ring, message, result);
            result = checkClockwiseCounterClowise(indexRing + 1, ring, message, result);
        }
        return result;
    }

    /**
     * Check whether the exterior ring is counter clockwise and the interior
     * rings are clockwised.
     *
     * @param numeroPolygon
     * @param vertices
     * @param message
     * @param isValidPolygon
     * @return
     */
    private boolean checkClockwiseCounterClowise(int numeroPolygon, double[][] vertices, StringBuilder message, boolean isValidPolygon) {
        if (numeroPolygon == 1) {
            if (Utils.isClockwisedPolygon(vertices)) {
                isValidPolygon = false;
                message.append("Polygon n°").append(numeroPolygon).append(" is not counterclockwise");
            }
        } else if (Utils.isCounterClockwisedPolygon(vertices)) {
            isValidPolygon = false;
            message.append("Polygon n°").append(numeroPolygon).append(" is not clockwise");
        }
        return isValidPolygon;
    }

    /**
     * Checks whether the polygon contains at least four points.
     *
     * @param numeroPolygon numero of the polygon (start to 1)
     * @param ring points in ring
     * @param message error message
     * @param isValidPolygon status of the current validation
     * @return True when the current validation is OK otherwise False
     */
    private boolean checkValidNumberPoint(int numeroPolygon, double[][] ring, StringBuilder message, boolean isValidPolygon) {
        return isValidNumberPoints(numeroPolygon, ring, message) ? isValidPolygon : false;
    }

    /**
     * Tests whether the polygon contains at least four points.
     *
     * @param numeroPolygon numero fo the polygon (starts to 1)
     * @param ring points in ring
     * @param message error message
     * @return True when the ring is valid otherwise false
     */
    private boolean isValidNumberPoints(int numeroPolygon, double[][] ring, StringBuilder message) {
        boolean result;
        if (ring.length < NB_POINTS) {
            result = false;
            message.append("Polygon n°").append(numeroPolygon).append(": only ").append(ring.length).append(" points detected - A polygon must be composed at least of four points\n");
        } else {
            result = true;
        }
        return result;
    }

    /**
     * Checks whether the polygon is closed
     *
     * @param numeroPolygon polygon number (starts to 1)
     * @param ring points in ring
     * @param message error message
     * @param isValidPolygon current status of the polygon validation
     * @return True when the current status of validation is OK otherwise False
     */
    private boolean checkClosedPolygon(int numeroPolygon, double[][] ring, StringBuilder message, boolean isValidPolygon) {
        return isClosedPolygon(numeroPolygon, ring, message) ? isValidPolygon : false;
    }       

    /**
     * Tests whether the polygon is closed.
     *
     * @param numeroPolygon polygon number (starts to 1)
     * @param ring points in ring
     * @param message error message
     * @return True when the polygon is closed otherwise False
     */
    private boolean isClosedPolygon(int numeroPolygon, double[][] ring, StringBuilder message) {
        boolean result;
        int nbPointsInRing = ring.length;
        double[] firstPt = ring[0];
        double[] lastPt = ring[nbPointsInRing - 1];
        result = Arrays.equals(firstPt, lastPt);
        if (!result) {
            message.append("Polygon n°").append(numeroPolygon).append(": the polygon is not closed\n");
        }
        return result;
    }

    @Override
    public final void setPoints(double[][][] points) {
        fixClosePolygon(points);
        fixClockwise(points);
        fixLongitude(points);
        super.setPoints(points);
        checkPolygon(points);
    }
    
    private void fixClosePolygon(double[][][] rings) {
        if(this.getOptions().containsKey(GeoJsonWriter.CLOSE_POLYGON)
                && (boolean)this.getOptions().get(GeoJsonWriter.CLOSE_POLYGON)) {
            int ringIndex = 0;
            for(double[][] ring:rings) {
                if(!Arrays.equals(ring[0], ring[ring.length-1])) {
                    double[][] newRing = new double[ring.length+1][];
                    System.arraycopy(ring, 0, newRing, 0, ring.length);
                    newRing[ring.length] = newRing[0];
                    rings[ringIndex] = newRing;
                }
                ringIndex++;
            }
        }
    }
    
    private void fixClockwise(double[][][] points) {
        if(this.getOptions().containsKey(GeoJsonWriter.FIX_CLOCKWISE)
                && (boolean)this.getOptions().get(GeoJsonWriter.FIX_CLOCKWISE)) {            
            double[][] exteriorRing = points[0];
            
            // exterior ring must be in counter clockwise
            if (Utils.isClockwisedPolygon(exteriorRing)) {
                Utils.reverse(exteriorRing);
            }
            
            // interior rings must be in clockwise
            for (int i = 1; i < points.length; i++) {
                double[][] interiorRing = points[i];
                if (Utils.isCounterClockwisedPolygon(interiorRing)) {
                    Utils.reverse(interiorRing);
                }
            }            
        }
    }
    
    /**
     * Fix longitudes.
     * @param multiLines 
     */
    private void fixLongitude(double[][][] multiLines) {
        if (this.getOptions().containsKey(GeoJsonWriter.FIX_LONGITUDE)
                && (boolean) this.getOptions().get(GeoJsonWriter.FIX_LONGITUDE)) {
            for (double[][] line : multiLines) {
                for (double[] coordinate : line) {
                    if (coordinate[0] > 180.0) {
                        coordinate[0] -= 360.0;
                    }
                }                
            }
        }
    }    

    /**
     * Sets the points.
     * @param exteriorRing exterior ring
     * @param interiorRings home
     */
    public final void setPoints(LineString[] exteriorRing, List<LineString[]> interiorRings) {
        double[][][] points = computePolygonFromLineStrings(exteriorRing, interiorRings);
        setPoints(points);
    }      

    /**
     * Computes polygon from an array of LineString
     * @param exteriorRing exterior ring
     * @param interiorRings holes
     * @return polygon
     */
    private double[][][] computePolygonFromLineStrings(LineString[] exteriorRing, List<LineString[]> interiorRings) {
        double[][][] points = new double[1 + interiorRings.size()][][];
        points[0] = computePolygonFromLineStrings(exteriorRing);
        for (int i = 0; i < interiorRings.size(); i++) {
            points[i + 1] = computePolygonFromLineStrings(interiorRings.get(i));
        }
        return points;
    }

    /**
     * Computes polygon from an array of LineString.
     *
     * @param lineStrings array of lineString drawing the polygon
     * @return the polygon
     */
    private double[][] computePolygonFromLineStrings(LineString[] lineStrings) {
        int nbDims = lineStrings[0].toPositionArray()[0].length();
        int size = 0;
        for (LineString lineString : lineStrings) {
            size += lineString.nbPositions();
        }
        size -= lineStrings.length - 1;
        double[][] points = new double[size][nbDims];

        int destPos = 0;
        for (int i = 0; i < lineStrings.length; i++) {
            double[][] lineString = lineStrings[i].toDouble2DArray();
            System.arraycopy(lineString, 0, points, destPos, lineString.length);
            destPos += lineString.length - 1;
        }
        points[points.length - 1] = points[0];
        return points;
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
        Polygon polygon = (Polygon) obj;
        if(this.getBbox() == null) {
            if(polygon.getBbox() != null) {
                return false;
            }
        } else if(!Arrays.equals(this.getBbox(), polygon.getBbox())) {
            return false;            
        }
        if(this.getCrs() == null) {
            if(polygon.getCrs() != null) {
                return false;
            }
        } else if(!this.getCrs().equals(polygon.getCrs())) {
            return false;
        }        
        
        if(!this.getType().equals(polygon.getType())) {
            return false;
        }         
        
        if(this.getForeignMembers() == null) {
            if(polygon.getForeignMembers() != null) {
                return false;
            }
        } else if(!this.getForeignMembers().equals(polygon.getForeignMembers())) {
            return false;
        }
        
        if(this.getCoordinates() == null) {
            if(polygon.getCoordinates() != null) {
                return false;
            }
        } else if(!Arrays.deepEquals(this.getCoordinates(), polygon.getCoordinates())) {
            return false;            
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 8;
        hash = 89 * hash + super.hashCode();
        if (this.getCoordinates() != null) {
            int result = getCoordinates().length;
            for (int i = 0; i < result; i++) {
                double[][] value = getCoordinates()[i];
                LineString ls = new LineString(value);
                hash = hash * 89 + ls.hashCode();
            }
        }
        return hash;
    }

}
