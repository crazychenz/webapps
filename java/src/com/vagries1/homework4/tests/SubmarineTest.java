/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.vagries1.homework4.Submarine;
import java.util.zip.CRC32;
import org.junit.Test;

public class SubmarineTest {

    @Test
    public void saneAccessorsTests() {
        Submarine obj = new Submarine();
        CRC32 crc = new CRC32();

        // Check normal setNumberTorpedos with int
        obj.setNumberTorpedos(4);
        assertEquals(4, obj.getNumberTorpedos());

        // Check normal setNumberTorpedos with string
        obj.setNumberTorpedos("7");
        assertEquals(7, obj.getNumberTorpedos());

        // Check explicit constructor toString() output
        crc.update(obj.toString().getBytes());
        assertEquals(2229414525L, crc.getValue());

        // Check default constructor toString() output
        crc.reset();
        crc.update(new Submarine().toString().getBytes());
        assertEquals(2175614200L, crc.getValue());
    }

    @Test
    public void exceptionAccessorTests() {
        Submarine obj = new Submarine();

        // Check setNumberMissiles lower boundary
        try {
            obj.setNumberTorpedos(obj.MIN_TORPEDOS - 1);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("torpedo count negative", e.getMessage());
        }

        // Check setNumberMissiles with null
        try {
            obj.setNumberTorpedos(null);
            fail();
        } catch (NumberFormatException e) {
            assertEquals("null", e.getMessage());
        }

        // Check setNumberMissiles with non arabic numeral String
        try {
            obj.setNumberTorpedos("not a number");
            fail();
        } catch (NumberFormatException e) {
        }
    }
}
