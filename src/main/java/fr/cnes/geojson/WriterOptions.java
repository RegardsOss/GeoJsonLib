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
package fr.cnes.geojson;

import java.util.Map;

/**
 * Interface to handle options for the GeoJsonWriter.
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public interface WriterOptions {
    
    /**
     * Sets the options.
     * @param options options
     */
    public void setOptions(final Map<String, Object> options);
    
    /**
     * Returns the options.
     * @return the options
     */
    public Map<String, Object> getOptions();    
    
}
