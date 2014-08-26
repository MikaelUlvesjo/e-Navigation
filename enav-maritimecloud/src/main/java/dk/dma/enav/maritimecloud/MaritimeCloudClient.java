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
package dk.dma.enav.maritimecloud;

import java.util.concurrent.TimeUnit;

import dk.dma.enav.maritimecloud.broadcast.BroadcastFuture;
import dk.dma.enav.maritimecloud.broadcast.BroadcastListener;
import dk.dma.enav.maritimecloud.broadcast.BroadcastMessage;
import dk.dma.enav.maritimecloud.broadcast.BroadcastOptions;
import dk.dma.enav.maritimecloud.broadcast.BroadcastSubscription;
import dk.dma.enav.maritimecloud.service.ServiceLocator;
import dk.dma.enav.maritimecloud.service.invocation.InvocationCallback;
import dk.dma.enav.maritimecloud.service.registration.ServiceRegistration;
import dk.dma.enav.maritimecloud.service.spi.ServiceInitiationPoint;
import dk.dma.enav.maritimecloud.service.spi.ServiceMessage;
import dk.dma.enav.model.MaritimeId;

/**
 * A client that can be used to access the e-navigation network.
 * 
 * @author Kasper Nielsen
 */
public interface MaritimeCloudClient extends AutoCloseable {

    /**
     * Blocks until all tasks have completed execution after a close request, or the timeout occurs, or the current
     * thread is interrupted, whichever happens first.
     * 
     * @param timeout
     *            the maximum time to wait
     * @param unit
     *            the time unit of the timeout argument
     * @return {@code true} if this client terminated and {@code false} if the timeout elapsed before termination
     * @throws InterruptedException
     *             if interrupted while waiting
     */
    boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;

    /**
     * Broadcasts the specified message. No guarantees are made to the delivery of the specified message.
     * <p>
     * This method uses the the default options set by
     * {@link MaritimeCloudClientConfiguration#setDefaultBroadcastOptions(BroadcastOptions)}.
     * 
     * @param message
     *            the message to broadcast
     * @throws NullPointerException
     *             if the specified message is null
     * @throws ConnectionClosedException
     *             if the connection has been permanently closed
     * @see #broadcast(BroadcastMessage, BroadcastOptions)
     */
    BroadcastFuture broadcast(BroadcastMessage message);

    /**
     * Broadcasts the specified message. No guarantees are made to the delivery of the specified message.
     * 
     * @param message
     *            the message to broadcast
     * @throws NullPointerException
     *             if the specified message is null
     * @throws ConnectionClosedException
     *             if the connection has been permanently closed
     */
    BroadcastFuture broadcast(BroadcastMessage message, BroadcastOptions options);

    /**
     * Subscribes to the the specified type of broadcast messages.
     * 
     * @param messageType
     *            the type of message to listen for
     * @param consumer
     *            the consumer of messages
     * @return a subscription that can be used to cancel the subscription
     * @throws NullPointerException
     *             if the message type of consumer is null
     * @throws ConnectionClosedException
     *             if the connection has been permanently closed
     */
    <T extends BroadcastMessage> BroadcastSubscription broadcastListen(Class<T> messageType,
            BroadcastListener<T> consumer);

    /**
     * Asynchronously shutdowns this client. use {@link #awaitTermination(long, TimeUnit)} to await complete termination
     * (acknowledgment by the remote side).
     */
    void close();

    /**
     * Returns details about the connection.
     * 
     * @return connection details
     */
    MaritimeCloudConnection connection();

    /**
     * Returns the id of this client.
     * 
     * @return the id of this client
     */
    MaritimeId getClientId();

    /**
     * Returns {@code true} if this executor has been shut down.
     * 
     * @return {@code true} if this executor has been shut down
     */
    boolean isClosed();

    /**
     * Returns {@code true} if all tasks have completed following shut down. Note that {@code isTerminated} is never
     * {@code true} unless either {@code shutdown} or {@code shutdownNow} was called first.
     * 
     * @return {@code true} if all tasks have completed following shut down
     */
    boolean isTerminated();

    /**
     * Invokes the specified service.
     * 
     * @param id
     *            the id of the owner of the service
     * @param initiatingServiceMessage
     *            the initiating service message
     * @return a future with the result
     * 
     * @throws ConnectionClosedException
     *             if the connection has been permanently closed
     */
    <T, S extends ServiceMessage<T>> ConnectionFuture<T> serviceInvoke(MaritimeId id, S initiatingServiceMessage);

    /**
     * Creates a ServiceLocator for a service of the specified type.
     * 
     * @param sip
     *            the service initiation point
     * @return a service locator object
     * 
     * @throws ConnectionClosedException
     *             if the connection has been permanently closed
     */
    <T, S extends ServiceMessage<T>> ServiceLocator<T, S> serviceLocate(ServiceInitiationPoint<S> sip);

    /**
     * Registers the specified service with the maritime cloud. If a client is closed via
     * {@link MaritimeCloudClient#close()} the server will automatically disregister all services.
     * 
     * @param sip
     *            the type of service
     * @param callback
     *            the callback that will be invoked by remote clients
     * @return a service registration object
     * 
     * @throws ConnectionClosedException
     *             if the connection has been permanently closed
     */
    <T, S extends ServiceMessage<T>> ServiceRegistration serviceRegister(ServiceInitiationPoint<S> sip,
            InvocationCallback<S, T> callback);
}
