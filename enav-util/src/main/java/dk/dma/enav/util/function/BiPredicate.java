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
package dk.dma.enav.util.function;

/**
 * 
 * @author Kasper Nielsen
 */
public interface BiPredicate<T, U> {

    /**
     * Returns <code>true</code> if the inputs match some criteria, otherwise false.
     * 
     * @param t
     *            the first argument
     * @param u
     *            the second argument
     * @return <code>true</code> if the inputs match some criteria
     */
    boolean test(T t, U u);
}
