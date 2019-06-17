/* Copyright © 2019
 * Vincent Agriesti
 * All rights reserved.
 *
 */

package com.vagries1.homework5;

/*
badDay() ... bad code ... doesn't account for % 4 % 100.

isValidDate() docs are out of date.

*/

public class BhcAppointment {

    private int year = 0;
    private int month = 0;
    private int date = 0;

    public BhcAppointment(int month, int date, int year) {
        // Throw exception on bad dates.
        dateValid(month, date, year);
        this.month = month;
        this.date = date;
        this.year = year;
    }

    void dateValid(int month, int date, int year) {
        // Sanity check month input.
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("invalid month");
        }

        // Sanity check date lower bound.
        if (date < 1) {
            throw new IllegalArgumentException("date to low");
        }

        // Handle the leap year mess and the alternating month ending.
        if (month == 2) {
            if (year % 4 == 0) {
                if (year % 100 == 0) {
                    if (year % 400 == 0) {
                        if (date > 29) {
                            String msg = "29 days in feb 400 year leap year";
                            throw new IllegalArgumentException(msg);
                        }
                    } else if (date > 28) {
                        String msg = "28 days in feb 100 year leap year";
                        throw new IllegalArgumentException(msg);
                    }
                } else if (date > 29) {
                    String msg = "29 days in feb leap year";
                    throw new IllegalArgumentException(msg);
                }
            } else if (date > 28) {
                String msg = "Feb only has 28 days every non-leap year.";
                throw new IllegalArgumentException(msg);
            }
        } else if (month == 1
                || month == 3
                || month == 5
                || month == 7
                || month == 8
                || month == 10
                || month == 12) {
            if (date > 31) {
                String msg = "Given month only has 31 days.";
                throw new IllegalArgumentException(msg);
            }
        } else if (date > 30) {
            String msg = "Given month only has 30 days.";
            throw new IllegalArgumentException(msg);
        }
    }
}
