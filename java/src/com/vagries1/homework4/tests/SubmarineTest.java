/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;

import com.vagries1.homework4.Submarine;
import org.junit.Test;

public class SubmarineTest {

    @Test
    public void saneAccessorsTests() {
        Submarine obj = new Submarine();

        obj.setNumberTorpedos(4);
        assertEquals(4, obj.getNumberTorpedos());

        obj.setNumberTorpedos("7");
        assertEquals(7, obj.getNumberTorpedos());
    }
}
