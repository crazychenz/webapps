/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.vagries1.homework4.Destroyer;
import com.vagries1.homework4.Ship;
import java.util.zip.CRC32;
import org.junit.Test;

public class ShipTest {

    @Test
    public void saneAccessorsTests() {
        Ship obj = new Destroyer();
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

        // Check explicit constructor toString() output
        crc.update(obj.toString().getBytes());
        assertEquals(4078064716L, crc.getValue());

        // Check default constructor toString() output
        crc.reset();
        crc.update(new Destroyer().toString().getBytes());
        assertEquals(2005370870L, crc.getValue());
    }

    @Test
    public void exceptionAccessorTests() {
        Ship obj = new Destroyer();

        // Check setLength upper boundary
        try {
            obj.setLength(obj.MAX_LENGTH + 1);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("length too high", e.getMessage());
        }

        // Check setLength low boundary
        try {
            obj.setLength(obj.MIN_LENGTH - 1);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("length negative", e.getMessage());
        }

        // Check setSpeed upper boundary
        try {
            obj.setSpeed(obj.MAX_SPEED + 1);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("speed too high", e.getMessage());
        }

        // Check setSpeed lower boundary
        try {
            obj.setSpeed(obj.MIN_SPEED - 1);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("speed negative", e.getMessage());
        }

        // Check setSpeed with null
        try {
            obj.setSpeed(null);
            fail();
        } catch (NumberFormatException e) {
            assertEquals("null", e.getMessage());
        }

        // Check setSpeed with non arabic numeral String
        try {
            obj.setSpeed("not a number");
            fail();
        } catch (NumberFormatException e) {
        }

        // Check setName with null
        try {
            obj.setName(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("name is null", e.getMessage());
        }

        // Check setName with empty name
        try {
            obj.setName("");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("name is empty", e.getMessage());
        }

        // Check setType with null
        try {
            obj.setType(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("type is null", e.getMessage());
        }

        // Check setType with empty type
        try {
            obj.setType("");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("type is empty", e.getMessage());
        }
    }
}
