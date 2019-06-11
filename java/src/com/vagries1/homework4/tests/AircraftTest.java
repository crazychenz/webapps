/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.vagries1.homework4.Aircraft;
import com.vagries1.homework4.P3;
import java.util.zip.CRC32;
import org.junit.Test;

public class AircraftTest {

    @Test
    public void saneAccessorTests() {
        Aircraft obj = new P3();
        CRC32 crc = new CRC32();

        // Check normal setLength
        obj.setLength(23);
        assertEquals(23, obj.getLength());

        // Check normal setName
        obj.setName("namero");
        assertEquals("namero", obj.getName());

        // Check normal setSpeed from String
        obj.setSpeed("56");
        assertEquals(56, obj.getSpeed());

        // Check normal setSpeed with int
        obj.setSpeed(67);
        assertEquals(67, obj.getSpeed());

        // Check normal setType
        obj.setType("something");
        assertEquals("something", obj.getType());

        // Check normal altitude
        obj.setAltitude(1000);
        assertEquals(1000, obj.getAltitude());

        // Check explicit constructor toString() output
        crc.update(obj.toString().getBytes());
        // System.out.println(crc.getValue());
        assertEquals(2522750567L, crc.getValue());

        // Check default constructor toString() output
        crc.reset();
        crc.update(new P3().toString().getBytes());
        assertEquals(1519927304L, crc.getValue());
    }

    @Test
    public void exceptionAccessorTests() {
        Aircraft obj = new P3();

        // Check setAltitude upper boundary
        try {
            obj.setAltitude(obj.MAX_ALTITUDE + 1);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("altitude too high", e.getMessage());
        }

        // Check setAltitude lower boundary
        try {
            obj.setAltitude(obj.MIN_ALTITUDE - 1);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("altitude too low", e.getMessage());
        }
    }
}
