/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.vagries1.homework4.P3;
import java.util.zip.CRC32;
import org.junit.Test;

public class P3Test {

    @Test
    public void saneAccessorsTests() {
        P3 obj = new P3();
        CRC32 crc = new CRC32();

        // Check normal setNumberEngines with int
        obj.setNumberEngines(3);
        assertEquals(3, obj.getNumberEngines());

        // Check explicit constructor toString() output
        crc.update(obj.toString().getBytes());
        assertEquals(1491009105L, crc.getValue());

        // Check default constructor toString() output
        crc.reset();
        crc.update(new P3().toString().getBytes());
        assertEquals(1519927304L, crc.getValue());
    }

    @Test
    public void exceptionAccessorTests() {
        P3 obj = new P3();

        // Check setNumberEngines lower boundary
        try {
            obj.setNumberEngines(obj.MIN_ENGINES - 1);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertEquals("engine count negative", e.getMessage());
        }
    }
}
