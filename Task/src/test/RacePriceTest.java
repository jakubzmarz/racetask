package test;

import module.PriceModifier;
import module.RacePrice;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

public class RacePriceTest
{
    @Test
    public void getRacePriceTest()
    {
        RacePrice racePrice = new RacePrice(new BigDecimal(67.50), new BigDecimal(22.50), new BigDecimal(10));
        Assertions.assertEquals(racePrice.getFullRacePrice(),new BigDecimal(100));
    }

    @Test
    public void calculatePriceProportionTest()
    {

    }

    @Test
    public void getFinalPriceTest()
    {
        RacePrice racePrice = new RacePrice(new BigDecimal(15));
        PriceModifier priceModifier = new PriceModifier(racePrice,new BigDecimal(10));
        Assertions.assertEquals(new BigDecimal("10.00"), priceModifier.getFinalPrice());
    }

    @Test
    public void getCalculatedFinalPricesTest()
    {
        RacePrice racePrice = new RacePrice(new BigDecimal(15),new BigDecimal(1),new BigDecimal(2),new BigDecimal(3));
        PriceModifier priceModifier = new PriceModifier(racePrice,new BigDecimal(16));
        System.out.println(priceModifier.getFinalPrice());
    }
}
