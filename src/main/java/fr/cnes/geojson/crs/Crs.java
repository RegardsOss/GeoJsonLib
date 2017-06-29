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
package fr.cnes.geojson.crs;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The coordinate reference system (CRS) of a GeoJSON object is determined by
 * its "crs" member (referred to as the CRS object below). If an object has no
 * crs member, then its parent or grandparent object's crs member may be
 * acquired. If no crs member can be so acquired, the default CRS shall apply to
 * the GeoJSON object.
 * <ul>
 * <li>The default CRS is a geographic coordinate reference system, using the
 * WGS84 datum, and with longitude and latitude units of decimal degrees.</li>
 * <li>The value of a member named "crs" must be a JSON object (referred to as
 * the CRS object below) or JSON null. If the value of CRS is null, no CRS can
 * be assumed.</li>
 * <li>The crs member should be on the top-level GeoJSON object in a hierarchy
 * (in feature collection, feature, geometry order) and should not be repeated
 * or overridden on children or grandchildren of the object.</li>
 * <li>A non-null CRS object has two mandatory members: "type" and
 * "properties".</li>
 * <li>The value of the type member must be a string, indicating the type of CRS
 * object.</li>
 * <li>The value of the properties member must be an object.</li>
 * <li>CRS shall not change coordinate ordering</li>
 * </ul>
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class Crs {
    
    private static final Logger LOGGER = Logger.getLogger(Crs.class.getName());    

    /**
     * CRS type.
     */
    public TypeEnum type;

    /**
     * Crs properties.
     */
    public Map<String, Object> properties = new HashMap<>();

    /**
     * Types of enumerated types.
     */
    public enum TypeEnum {
        /**
         * A CRS object may link to CRS parameters on the Web. In this case, the
         * value of its "type" member must be the string "link", and the value
         * of its "properties" member must be a Link object
         */
        link,
        /**
         * A CRS object may indicate a coordinate reference system by name. In
         * this case, the value of its "type" member must be the string "name".
         * The value of its "properties" member must be an object containing a
         * "name" member. The value of that "name" member must be a string
         * identifying a coordinate reference system. OGC CRS URNs such as
         * "urn:ogc:def:crs:OGC:1.3:CRS84" shall be preferred over legacy
         * identifiers such as "EPSG:4326":
         */
        name
    }

    /**
     * Creates an empty Crs.
     */
    public Crs() {

    }

    /**
     * Returns the type of CRS.
     *
     * @return the type
     */
    public TypeEnum getType() {
        return type;
    }

    /**
     * Sets the type of CRS.
     *
     * @param type the type to set
     */
    public void setType(final TypeEnum type) {
        this.type = type;
    }

    /**
     * Returns the CRS properties.
     *
     * @return the properties
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * Sets the CRS properties.
     *
     * @param properties the properties to set
     */
    public void setProperties(final Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Crs crs = (Crs) obj;
        TypeEnum typeCrs = crs.getType();
        if (this.type == null) {
            if (typeCrs != null) {
                return false;
            }
        } else if (!this.type.equals(typeCrs)) {
            return false;
        }
        Map<String, Object> prop = crs.getProperties();
        if (this.properties == null) {
            if (prop != null) {
                return false;
            }
        } else if (!this.properties.equals(prop)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.type);
        hash = 67 * hash + Objects.hashCode(this.properties);
        return hash;
    }
}
