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
import java.util.logging.Logger;

/**
 * A position is an array of numbers. There MUST be two or three elements. The
 * first two elements are longitude and latitude, or easting and northing,
 * precisely in that order and using decimal numbers. Altitude or elevation MAY
 * be included as an optional third element.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class Position {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Position.class.getName());
    
    /**
     * longitude in decimal degree.
     */
    private final double longitude;
    
    /**
     * latitude in decimal degree.
     */
    private final double latitude;
    
    /**
     * altitude in meter.
     */
    private final double altitude;

    /**
     * Creates a position with a longitude, a latitude and an altitude.
     * @param longitude longitude in decimal degree
     * @param latitude latitude in decimal degree
     * @param altitude altitude in meter
     */
    public Position(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    /**
     * Creates a position with a longitude and a latitude.
     * @param longitude longitude in decimal degree
     * @param latitude latitude in decimal degree
     */
    public Position(double longitude, double latitude) {
        this(longitude, latitude, Double.NaN);
    }

    /**
     * Creates a position with a set of points.
     * A point contains at least a longitude and a latitude. In addition, a point
     * can contain a third dimension, which is the altitude. The longitude and
     * latitude are expressed in decimal degree whereas the latitude is expressed
     * in meter.
     * 
     * @param points points
     */
    public Position(double[] points) {
        this(points[0], points[1], points.length == 3 ? points[2] : Double.NaN);
    }

    /**
     * Returns the longitude in decimal degree.
     * @return the longitude in decimal degree
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns the latitude in decimal degree.
     * @return the latitude in decimal degree
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the altitude in meter.
     * @return the altitude in meter
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * Returns the position as a double array.
     * @return the position as a double array
     */
    public double[] toArray() {
        return Double.isNaN(getAltitude()) ? new double[]{getLongitude(), getLatitude()} : new double[]{getLongitude(), getLatitude(), getAltitude()};
    }

    /**
     * Returns the number of points.
     * @return the number of points
     */
    public int length() {
        return Double.isNaN(latitude) ? 2 : 3;
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

        Position position = (Position) obj;
        return Utils.equals(getLongitude(), position.getLongitude())
                && Utils.equals(getLatitude(), position.getLatitude())
                && ((Double.isNaN(getAltitude()) == true && Double.isNaN(position.getAltitude()) == true)
                || Utils.equals(getAltitude(), position.getAltitude()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.altitude) ^ (Double.doubleToLongBits(this.altitude) >>> 32));
        return hash;
    }

}
