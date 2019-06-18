/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 * Note: This code pattern is derived from Joshua Bloch. Effective Java.
 *
 * https://www.codeaffine.com/2015/03/04/map-distinct-value-types-using-java-generics
 *
 */

package com.vagries1.homework5;

public class GooeyElement {

    // private final Map<Class<?>, Object> values = new HashMap<>();
    private Class<?> type;
    private Object value;

    public <T> GooeyElement(Class<T> type, T value) {
        // values.put(key, value);
        this.type = type;
        this.value = value;
    }

    public <T> T get(Class<T> key) {
        // return key.cast(values.get(key));
        return key.cast(value);
    }

    public Object getValue() {
        return value;
    }
}
