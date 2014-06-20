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

package dk.dma.enav.model.geometry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoundingBoxTest {

    @Test
    public void getArea() {
        // http://www.movable-type.co.uk/scripts/latlong.html
        assertEquals(1.853*1.036, BoundingBox.create(Position.create(56.0, 10.0), Position.create(56.016667, 10.016667), CoordinateSystem.CARTESIAN).getArea(), 1e-3);
        assertEquals(1.853*1.848, BoundingBox.create(Position.create(56.0, 10.0), Position.create(56.016667, 10.029722), CoordinateSystem.CARTESIAN).getArea(), 1e-3);
    }

}
