/* Copyright (c) 2011 Danish Maritime Authority.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.dma.enav.maritimecloud.broadcast;

import static java.util.Objects.requireNonNull;
import dk.dma.enav.model.MaritimeId;
import dk.dma.enav.model.geometry.PositionTime;

/**
 * Properties conveyed to receivers of broadcast messages.
 * 
 * @author Kasper Nielsen
 */
public class BroadcastMessageHeader {

    /** The id of the ship sending the broadcast. */
    private final MaritimeId id;

    /** The position and time of the ship sending the broadcast. */
    private final PositionTime position;

    /**
     * @param id
     *            the id of the ship sending the broadcast
     * @param position
     *            the position and time of the ship sending the broadcast.
     * @throws NullPointerException
     *             if the id or position is null
     */
    public BroadcastMessageHeader(MaritimeId id, PositionTime position) {
        this.position = requireNonNull(position);
        this.id = requireNonNull(id);
    }

    /**
     * Returns the id of the ship sending the broadcast.
     * 
     * @return the id of the ship sending the broadcast
     */
    public MaritimeId getId() {
        return id;
    }

    /**
     * Returns the position and time of the ship sending the broadcast.
     * 
     * @return the position and time of the ship sending the broadcast
     */
    public PositionTime getPosition() {
        return position;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "id=" + id + ", position=" + position;
    }
}
