/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;

import com.vagries1.homework4.Destroyer;
import org.junit.Test;

public class DestroyerTest {

    @Test
    public void saneAccessorsTests() {
        Destroyer obj = new Destroyer();

        obj.setNumberMissiles("7");
        assertEquals(7, obj.getNumberMissiles());

        obj.setNumberMissiles(4);
        assertEquals(4, obj.getNumberMissiles());
    }
}
