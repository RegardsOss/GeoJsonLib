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

/**
 * A position is an array of numbers. There MUST be two or three elements. The
 * first two elements are longitude and latitude, or easting and northing,
 * precisely in that order and using decimal numbers. Altitude or elevation MAY
 * be included as an optional third element.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class Position {

    private final double longitude;
    private final double latitude;
    private final double altitude;

    public Position(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public Position(double longitude, double latitude) {
        this(longitude, latitude, Double.NaN);
    }

    public Position(double[] point) {
        this(point[0], point[1], point.length == 3 ? point[2] : Double.NaN);
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public double[] toArray() {
        return Double.isNaN(getAltitude()) ? new double[]{getLongitude(), getLatitude()} : new double[]{getLongitude(), getLatitude(), getAltitude()};
    }

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
