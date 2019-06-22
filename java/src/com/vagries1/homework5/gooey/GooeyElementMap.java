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

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Class that contains GooeyElement elements. This class assists by making the creation and
 * resolution of GooeyElement object casts more transparent. This class behaves much closer to a
 * standard HashMap with standard put() and get() like interfaces. The big difference is that in
 * this class all putAs() and getAs() calls require a Class reference to cast the object to under
 * the hood.
 *
 * @author Vincent Agriesti
 */
public class GooeyElementMap {

    private LinkedHashMap<String, GooeyElement> map;

    /** Default Constructor. */
    public GooeyElementMap() {
        map = new LinkedHashMap<String, GooeyElement>();
    }

    /**
     * Puts an object into the HashMap with type information for later casting.
     *
     * @param <T> The object type of value.
     * @param key The key to reference the object with in the HashMap
     * @param type The Class object to cast value with.
     * @param value The object reference to store in the HashMap
     */
    public <T> void putAs(String key, Class<T> type, T value) {
        map.put(key, new GooeyElement(type, value));
    }

    /**
     * Gets an object from a HashMap and casts to the requested type.
     *
     * @param <T> The object type of value.
     * @param key The key to reference the object with in the HashMap
     * @param type The Class object to cast value with.
     * @return Object casted to type T or null on failure.
     */
    public <T> T getAs(String key, Class<T> type) {
        try {
            return map.get(key).get(type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets a plain ole Object reference without any casting. This is good for Object reference
     * comparisons.
     *
     * @param key The key the object reference is stored with.
     * @return Object reference of value.
     */
    public Object get(String key) {
        Object obj = null;
        GooeyElement element = map.get(key);
        if (element != null) {
            obj = element.getValue();
        }
        return obj;
    }

    /**
     * Returns the list of keys in the HashMap.
     *
     * @return List of keys from the HashMap.
     */
    public Set<String> keySet() {
        return map.keySet();
    }
}
