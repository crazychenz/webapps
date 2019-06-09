/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;

import com.vagries1.homework4.Aircraft;
import com.vagries1.homework4.P3;
import org.junit.Test;

public class AircraftTest {

    @Test
    public void saneAccessorsTests() {
        Aircraft obj = new P3();

        obj.setLength(23);
        assertEquals(23, obj.getLength());

        obj.setName("namero");
        assertEquals("namero", obj.getName());

        obj.setSpeed("56");
        assertEquals(56, obj.getSpeed());

        obj.setSpeed(67);
        assertEquals(67, obj.getSpeed());

        obj.setType("something");
        assertEquals("something", obj.getType());

        obj.setAltitude(1000);
        assertEquals(1000, obj.getAltitude());
    }
}
