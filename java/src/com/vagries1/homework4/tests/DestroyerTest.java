/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.vagries1.homework4.Destroyer;
import java.util.zip.CRC32;
import org.junit.Test;

public class DestroyerTest {

    @Test
    public void saneAccessorsTests() {
        Destroyer obj = new Destroyer();
        CRC32 crc = new CRC32();

        // Check normal setNumberMissiles with String
        obj.setNumberMissiles("7");
        assertEquals(7, obj.getNumberMissiles());

        // Check setNumberMissiles with int
        obj.setNumberMissiles(4);
        assertEquals(4, obj.getNumberMissiles());

        // Check explicit constructor toString() output
        crc.update(obj.toString().getBytes());
        assertEquals(1888366378L, crc.getValue());

        // Check default constructor toString() output
        crc.reset();
        crc.update(new Destroyer().toString().getBytes());
        assertEquals(2005370870L, crc.getValue());
    }

    @Test
    public void exceptionAccessorTests() {
        Destroyer obj = new Destroyer();

        // Check setNumberMissiles lower boundary
        try {
            obj.setNumberMissiles(obj.MIN_MISSILES - 1);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("missiles count negative", e.getMessage());
        }

        // Check setNumberMissiles with null
        try {
            obj.setNumberMissiles(null);
            fail();
        } catch (NumberFormatException e) {
            assertEquals("null", e.getMessage());
        }

        // Check setNumberMissiles with non arabic numeral String
        try {
            obj.setNumberMissiles("not a number");
            fail();
        } catch (NumberFormatException e) {
        }
    }
}
