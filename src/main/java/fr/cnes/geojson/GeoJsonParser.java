/**
 * ****************************************************************************
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
 * ****************************************************************************
 */
package fr.cnes.geojson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.cnes.geojson.gson.FeatureCollectionSerializer;
import fr.cnes.geojson.gson.FeatureSerializer;
import fr.cnes.geojson.gson.GeometrySerializer;
import fr.cnes.geojson.object.Feature;
import fr.cnes.geojson.object.FeatureCollection;
import fr.cnes.geojson.object.Geometry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Creates a GeoJSON parser.
 *
 * @author Jean-Christophe Malapert (jean-christophe.malapert@cnes.fr)
 */
public class GeoJsonParser extends AbstractGeoJsonUtility {
    
    private static final Logger LOGGER = Logger.getLogger(GeoJsonParser.class.getName());

    public GeoJsonParser() {
        super();
    }

    /**
     * Tests whether the GeoJSON is a FeatureCollection.
     *
     * @param geojson GeoJSON
     * @return True when the GeoJSON is a FeatureCollection otherwise False.
     */
    public static boolean isFeatureCollection(final String geojson) {
        return geojson.contains("FeatureCollection");
    }

    /**
     * Parses a GeoJSON and returns a Feature or FeatureCollection.
     *
     * @param <T> Feature or FeatureCollection
     * @param geojson GeoJSON
     * @return Feature or FeatureCollection
     */
    public <T> T parse(final String geojson) {
        Gson gson = this.getGsonBuilder().create();
        return gson.fromJson(geojson, isFeatureCollection(geojson) ? FeatureCollection.class : Feature.class);
    }

    /**
     * Parses a GeoJSON and returns a Feature or FeatureCollection.
     *
     * @param <T> Feature or FeatureCollection
     * @param url URL of the GeoJSON file
     * @return Feature or FeatureCollection
     * @throws IOException Will throw an error when the content cannot be
     * retrieved
     */
    public <T> T parse(final URL url) throws IOException {
        InputStream is = url.openConnection().getInputStream();
        return parse(is);
    }

    /**
     * Parses a GeoJSON and returns a Feature or FeatureCollection.
     *
     * @param <T> Feature or FeatureCollection
     * @param file GeoJSON file
     * @return Feature or FeatureCollection
     * @throws java.io.FileNotFoundException Will throw an error when the file
     * cannot be found
     * @throws IOException Will throw an error when the content cannot be
     * retrieved
     */
    public <T> T parse(final File file) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(file);
        return parse(fis);
    }

    /**
     * Parses a GeoJSON and returns a Feature or FeatureCollection.
     *
     * @param <T> Feature or FeatureCollection
     * @param reader Buffer on the content of the GeoJSON file
     * @return Feature or FeatureCollection
     * @throws IOException Will throw an error when the content cannot be
     * retrieved
     */
    public <T> T parse(final BufferedReader reader) throws IOException {
        StringBuilder geojson = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            geojson.append(line);
        }
        reader.close();
        return parse(geojson.toString());
    }

    /**
     * Parses a GeoJSON and returns a Feature or FeatureCollection.
     *
     * @param <T> Feature or FeatureCollection
     * @param is InputStream of the GeoJSON file
     * @return Feature or FeatureCollection
     * @throws IOException Will throw an error when the content cannot be
     * retrieved
     */
    public <T> T parse(final InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return parse(reader);
    }

}
