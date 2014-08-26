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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import dk.dma.enav.model.MaritimeId;
import dk.dma.enav.model.geometry.PositionTime;

/**
 * Abstract class for a message that can be broadcast.
 * <p>
 * Any implementation of this message must contain a <tt>public static final String CHANNEL = "name"</tt> field.
 * Initialized to a unique channel that the broadcast is send via.
 * 
 * @author Kasper Nielsen
 */
public abstract class BroadcastMessage {

    static final ConcurrentHashMap<String, Class<? extends BroadcastMessage>> CACHE = new ConcurrentHashMap<>();

    /**
     * Returns the broadcast channel on which the broadcast is sent. Defaults (for now) to the name of the class.
     * 
     * @return the broadcast channel on which the message should be sent
     */
    public final String channel() {
        return getClass().getCanonicalName();
    }

    static ServiceLoader<BroadcastMessage> sl = ServiceLoader.load(BroadcastMessage.class);

    /**
     * Finds the broadcast message for the specified channel.
     * 
     * @param channel
     *            the name of the channel
     * @return the corresponding to the specified channel
     */
    public static Class<? extends BroadcastMessage> findClass(String channel) {
        requireNonNull("channel is null");
        for (BroadcastMessage m : sl) {
            if (getChannelField(m.getClass()).equals("channel")) {
                return m.getClass();
            }
        }
        return null;
    }

    public static String findChannelForMessageType(Class<? extends BroadcastMessage> type) {
        String channel = getChannelField(type);

        return channel;
    }

    static String getChannelField(Class<? extends BroadcastMessage> type) {
        Field f;
        try {
            f = type.getField("CHANNEL");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("No field named CHANNEL found on " + type.getCanonicalName(), e);
        }
        if (!Modifier.isFinal(f.getModifiers())) {
            throw new RuntimeException("Field named CHANNEL on " + type.getCanonicalName() + " must be final");
        } else if (!Modifier.isStatic(f.getModifiers())) {
            throw new RuntimeException("Field named CHANNEL on " + type.getCanonicalName() + " must be static");
        } else if (!Modifier.isPublic(f.getModifiers())) {
            throw new RuntimeException("Field named CHANNEL on " + type.getCanonicalName() + " must be public");
        } else if (f.getType() != String.class) {
            throw new RuntimeException("Field named CHANNEL on " + type.getCanonicalName()
                    + " must be of a String type");
        }
        try {
            return (String) f.get(null);
        } catch (IllegalArgumentException e) {
            throw new Error("oops, should never happen since the field is a string", e);
        } catch (IllegalAccessException e) {
            throw new Error("oops, should never happen since the field is public", e);
        }
    }


    /** An acknowledgment that can be send every time a broadcast is received by an actor. */
    public interface Ack {

        /**
         * Returns the id of the actor that received the broadcast.
         * 
         * @return the id of the actor that received the broadcast
         */
        MaritimeId getId();

        /**
         * Returns the position and time when the actor received the message.
         * 
         * @return the position and time when the actor received the message
         */
        PositionTime getPosition();
    }
}
