package com.fitzgerald;

public class StayPriceCalculator 
{

    public static double calculateStayPrice(
            int nights,
            int guestAge,
            boolean isArkansasResident,
            boolean hasVeteranStatus
    ) {

        // ERROR CASES
        if (nights < 1 || nights > 14) 
            {
            throw new IllegalArgumentException("Invalid number of nights");
        }

        if (guestAge < 0)
             {
            throw new IllegalArgumentException("Invalid guest age");
        }

        // BASE PRICE
        double totalPrice = 50.0 * nights;

        // guestAge rules (EP/BVA)
        if (guestAge >= 0 && guestAge <= 12) 
            {
            totalPrice *= 0.50; // 50% off
        } else if (guestAge >= 13 && guestAge <= 64)
             {
            // full price 
        } else { // guestAge >= 65
            totalPrice *= 0.80; // 20% off
        }

        // decision table
        if (isArkansasResident && hasVeteranStatus) 
            {
            totalPrice -= 10.0;  // resident discount first
            totalPrice *= 0.90;  // veteran discount
        } else if (isArkansasResident) 
            {
            totalPrice -= 10.0;
        } else if (hasVeteranStatus) 
            {
            totalPrice *= 0.90;
        }

        return totalPrice;
    }
}
