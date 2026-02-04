package com.fitzgerald;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StayPriceCalculatorTest {

    // -------------------- ERROR CASES ------------------- //
    @Test
    // ERROR CASE T1.4 | EP
    void negativeAge_InvalidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            StayPriceCalculator.calculateStayPrice (
                1,  // nights
                -20,    // guestAge
                false,  // AR resident
                false   // veteran
            );
        });
    }

    @Test
    // ERROR CASE T2.4 | BVA
    void invalidNights_Test () {
        assertThrows(IllegalArgumentException.class, () -> {
            StayPriceCalculator.calculateStayPrice (
                15, // nights (outside BV)
                64, // guestAge
                false,
                false
            );
        });
    }

    // -------------------- EQUIVALENCE PARTITIONS ------------------- //

    @Test
    // T1.1 | EP
    void applycChildDiscount_Test () {
        double price = StayPriceCalculator.calculateStayPrice(1, 9, false, false);

        assertEquals(25.0, price); 
    }

    @Test
    // T1.2 | EP
    void fullPrice_Test() {
        double price = StayPriceCalculator.calculateStayPrice(1, 30, false, false);

        assertEquals(50.0, price);
    }

    @Test
    // T1.3 | EP
    void applySeniorDiscount_Test () {
        double price = StayPriceCalculator.calculateStayPrice(1, 95, false, false);

        assertEquals(40.0, price);
    }

    // -------------------- BOUNDARY VALUES ------------------- //

    @Test
    // T2.1 | BV
    void child_nightsLowerBoundary_Test() {
        double price = StayPriceCalculator.calculateStayPrice(1, 0, false, false); // child == 0 && nigths == 1

        assertEquals(25.0, price); 
    }


    @Test
    // T2.2 | BV
    void child_nightsUpperBoundary_Test() {
        double price = StayPriceCalculator.calculateStayPrice(14, 12, false, false); // age == 12 && nights = 14

        assertEquals(350.0, price);
    }


    @Test
    // T2.3 | BV
    void adult_nightsLowerBoundary_Test() {
        double price = StayPriceCalculator.calculateStayPrice(1, 13, false, false); // age == 13 && nights == 1

        assertEquals(50.0, price);
    }

    // T2.4 is an error case not covered in this section 

    @Test
    // T2.5 | BV
    void senior_nightBoundary_Test() {
        double price = StayPriceCalculator.calculateStayPrice(1, 65, false, false); // age == 65 && nights == 1 

        assertEquals(40.0, price);
    }

    // T2.6 isn't covered here explicitly because it is implied by T2.5
    // T2.7 isn't covered here because it duplicates the same outcome as T2.4
    

    // -------------------- DECISION TABLES ------------------- //

    @Test
    // T3.1 | DT
    void noDiscount_Test() {
        double price = StayPriceCalculator.calculateStayPrice(4, 30, false, false);

        assertEquals(200, price);
    }

    @Test
    // T3.2 | DT
    void arResidentDiscount_Test() {
        double price = StayPriceCalculator.calculateStayPrice(4, 30, true, false);

        assertEquals(190, price);
    }

    @Test
    // T3.3 | DT
    void veteranDiscount_Test() {
        double price = StayPriceCalculator.calculateStayPrice(4, 30, false, true);

        assertEquals(180, price);
    }

    @Test
    // T3.4 | DT
    void bothARResdentAndVeteran_Test() {
        double price = StayPriceCalculator.calculateStayPrice(4, 30, true, true);

        assertEquals(171, price);
    }

}
