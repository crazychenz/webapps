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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    /** Log4j logger object instance for this class. */
    private static final Logger logger = LogManager.getLogger(GooeyElementMap.class);

    /**
     * Map class that stores the type of an element as the key and the object of the element as the
     * value. When retrieving the object with the type class, the Object type of checked and thereby
     * avoiding the CastClassException that occurs all to often when storing multiple types in a
     * single container in Java.
     */
    private class GooeyElement {

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
            T ret = key.cast(value);
            return ret;
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
        T ret = null;
        try {
            ret = map.get(key).get(type);
        } catch (Exception e) {
            String msg = "Failed to locate specified map key: " + key;
            logger.error(msg);
            logger.debug("", e);
            ret = null;
        }
        return ret;
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
        Set<String> ret = map.keySet();
        return ret;
    }
}
