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

import java.util.LinkedHashMap;
import java.util.Set;

public class GooeyElementMap {

    GooeyProperties p;
    LinkedHashMap<String, GooeyElement> map;

    public GooeyElementMap() {
        p = new GooeyProperties();
        map = new LinkedHashMap<String, GooeyElement>();
    }

    public <T> void putAs(String key, Class<T> type, T value) {
        map.put(key, new GooeyElement(type, value));
    }

    public <T> T getAs(String key, Class<T> type) {
        try {
            return map.get(key).get(type);
        } catch (Exception e) {
            return null;
        }
    }

    public Object get(String key) {
        return map.get(key).getValue();
    }

    public Set<String> keySet() {
        return map.keySet();
    }
}
