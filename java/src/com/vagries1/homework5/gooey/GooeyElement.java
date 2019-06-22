/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 * Note: This code pattern is derived from Joshua Bloch. Effective Java.
 *
 * https://www.codeaffine.com/2015/03/04/map-distinct-value-types-using-java-generics
 *
 */

package com.vagries1.homework5.gooey;

/**
 * Map class that stores the type of an element as the key and the object of the element as the
 * value. When retrieving the object with the type class, the Object type of checked and thereby
 * avoiding the CastClassException that occurs all to often when storing multiple types in a single
 * container in Java.
 *
 * @author Vincent Agriesti
 */
public class GooeyElement {

    // private final Map<Class<?>, Object> values = new HashMap<>();
    private Class<?> type;
    private Object value;

    /**
     * Constructor of map element.
     *
     * @param <T> Type of the value to be stored.
     * @param type The class property of the value type to be stored.
     * @param value The object reference of the object to be stored.
     */
    public <T> GooeyElement(Class<T> type, T value) {
        // values.put(key, value);
        this.type = type;
        this.value = value;
    }

    /**
     * Accessor for retrieving the object casted to desired type.
     *
     * @param <T> The type the element needs to cast to after retrieval.
     * @param key The class propery of the value type to be retrieved.
     * @return The object reference type casted to T
     */
    public <T> T get(Class<T> key) {
        // return key.cast(values.get(key));
        return key.cast(value);
    }

    /**
     * Uncasted retrieval of object. Good for reference comparisons.
     *
     * @return Object reference stored in value.
     */
    public Object getValue() {
        return value;
    }
}
