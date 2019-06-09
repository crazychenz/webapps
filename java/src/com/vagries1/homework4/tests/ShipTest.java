/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;

import com.vagries1.homework4.Destroyer;
import com.vagries1.homework4.Ship;
import org.junit.Test;

public class ShipTest {

    @Test
    public void saneAccessorsTests() {
        Ship obj = new Destroyer();

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
    }
}
