/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework4.tests;

import static org.junit.Assert.assertEquals;

import com.vagries1.homework4.P3;
import org.junit.Test;

public class P3Test {

    @Test
    public void saneAccessorsTests() {
        P3 obj = new P3();

        obj.setNumberEngines(3);
        assertEquals(3, obj.getNumberEngines());
    }
}
