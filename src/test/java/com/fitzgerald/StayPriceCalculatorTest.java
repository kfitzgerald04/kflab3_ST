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

        assertEquals(25.0, price, "Children discount should $25 for 1 night"); 
    }

    @Test
    // T1.2 | EP
    void fullPrice_Test() {
        double price = StayPriceCalculator.calculateStayPrice(1, 30, false, false);

        assertEquals(50.0, price, "Adults 13-64, pay the full price of $50 for 1 night");
    }

    @Test
    // T1.3 | EP
    void applySeniorDiscount_Test () {
        double price = StayPriceCalculator.calculateStayPrice(1, 95, false, false);

        assertEquals(40.0, price, "Senior discount should be $40 for 1 night");
    }

    // -------------------- BOUNDARY VALUES ------------------- //

    @Test
    // T2.1 | BV
    void child_nightsLowerBoundary_Test() {
        double price = StayPriceCalculator.calculateStayPrice(1, 0, false, false); // child == 0 && nigths == 1

        assertEquals(25.0, price, "Children discount should $25 for 1 night"); 
    }


    @Test
    // T2.2 | BV
    void child_nightsUpperBoundary_Test() {
        double price = StayPriceCalculator.calculateStayPrice(14, 12, false, false); // age == 12 && nights = 14

        assertEquals(350.0, price, "Children discount should $350 for 14 nights");
    }


    @Test
    // T2.3 | BV
    void adult_nightsLowerBoundary_Test() {
        double price = StayPriceCalculator.calculateStayPrice(1, 13, false, false); // age == 13 && nights == 1

        assertEquals(50.0, price, "Adults 13-64, pay the full price of $50 for 1 night");
    }

    // T2.4 is an error case not covered in this section 

    @Test
    // T2.5 | BV
    void senior_nightBoundary_Test() {
        double price = StayPriceCalculator.calculateStayPrice(1, 65, false, false); // age == 65 && nights == 1 

        assertEquals(40.0, price, "Senior discount should be $40 for 1 night");
    }

    // T2.6 isn't covered here explicitly because it is implied by T2.5
    // T2.7 isn't covered here because it duplicates the same outcome as T2.4
    

    // -------------------- DECISION TABLES ------------------- //

    @Test
    // T3.1 | DT
    void noDiscount_Test() {
        double price = StayPriceCalculator.calculateStayPrice(4, 30, false, false);

        assertEquals(200, price, "Non AR residents and Veterans pay the full price of $200 for 4 nights");
    }

    @Test
    // T3.2 | DT
    void arResidentDiscount_Test() {
        double price = StayPriceCalculator.calculateStayPrice(4, 30, true, false);

        assertEquals(190, price, "AR Residents receive $10 off total of $200 for 4 nights");
    }

    @Test
    // T3.3 | DT
    void veteranDiscount_Test() {
        double price = StayPriceCalculator.calculateStayPrice(4, 30, false, true);

        assertEquals(180, price, "Veterans receive 10% off total of $200 for 4 nights");
    }

    @Test
    // T3.4 | DT
    void bothARResdentAndVeteran_Test() {
        double price = StayPriceCalculator.calculateStayPrice(4, 30, true, true);

        assertEquals(171, price, "4 nights for an AR resident who is also a Veteran = $171");
    }

}
