/* Copyright (c) 2011 Danish Maritime Authority
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package dk.dma.enav.serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import dk.dma.enav.model.voyage.Route;

/**
 * 
 * @author Jesper Tejlgaard
 * 
 */
public abstract class RouteParser {

    RouteDefaults defaults = new RouteDefaults(); 
    
    public static RouteParser getRouteParser(File file) throws FileNotFoundException {
        String ext = getExtension(file.getName());
        switch (ext) {
        case "TXT":
            return new SimpleRouteParser(file);
        case "ROU":
            return new RouRouteParser(file);
//        case "RT3":
//            return new Rt3RouteParser(file);
        default:
            throw new IllegalArgumentException("Unknown file extension. Known extensions are 'TXT' and 'ROU'.");
//            return new PertinaciousRouteParser(file);
        }
    }

    public static RouteParser getRouteParser(String fileName, InputStream io) {
        String ext = getExtension(fileName);
        switch (ext) {
        case "TXT":
            return new SimpleRouteParser(io);
        case "ROU":
            return new RouRouteParser(io);
//        case "RT3":
//            return new Rt3RouteParser(file);
        default:
            throw new IllegalArgumentException("Unknown file extension. Known extensions are 'TXT' and 'ROU'.");
//            return new PertinaciousRouteParser(file);
        }
    }
    
    private static String getExtension(String fileName){
        int position = fileName.lastIndexOf('.');
        return fileName.substring(position + 1).trim().toUpperCase();        
    }
    
    public RouteParser defaults(RouteDefaults defaults){
        this.defaults = defaults;
        return this;
    }
    
    public RouteDefaults getDefaults(){
        return defaults;
    }
    
    public abstract Route parse() throws IOException;

}