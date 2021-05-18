package test;

import module.PriceModifier;
import module.RacePrice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class RacePriceTest
{

    @Test
    public void testFirstScenario()
    {
        RacePrice racePrice = new RacePrice(new BigDecimal(10),new BigDecimal(5),new BigDecimal(5));

        racePrice = PriceModifier.applyLimit(racePrice, BigDecimal.valueOf(10));
        Assertions.assertEquals(BigDecimal.valueOf(5.00).setScale(2),
                racePrice.getBasePrice());
        Assertions.assertEquals(BigDecimal.valueOf(2.50).setScale(2),
                racePrice.getTaxList().get(0));
        Assertions.assertEquals(BigDecimal.valueOf(2.50).setScale(2),
                racePrice.getTaxList().get(1));
    }

    @Test
    public void testSecondScenario()
    {
        RacePrice racePrice = new RacePrice(new BigDecimal(9),new BigDecimal(1),new BigDecimal(1), new BigDecimal(1));

        racePrice = PriceModifier.applyLimit(racePrice, BigDecimal.valueOf(6.00));
        Assertions.assertEquals(BigDecimal.valueOf(4.5).setScale(2),racePrice.getBasePrice());
        Assertions.assertEquals(BigDecimal.valueOf(0.5).setScale(2),racePrice.getTaxList().get(0));
        Assertions.assertEquals(BigDecimal.valueOf(0.5).setScale(2),racePrice.getTaxList().get(1));
        Assertions.assertEquals(BigDecimal.valueOf(0.5).setScale(2),racePrice.getTaxList().get(2));
    }
    
}
