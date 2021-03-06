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


    @Test
    public void testThirdScenario()
    {
        RacePrice racePrice = new RacePrice(new BigDecimal(10.23444),new BigDecimal(1),new BigDecimal(2), new BigDecimal(1));

        racePrice = PriceModifier.applyLimit(racePrice, BigDecimal.valueOf(6.999));
        Assertions.assertEquals(BigDecimal.valueOf(4.6).setScale(2),racePrice.getBasePrice());
    }

    @Test
    public void testFourthScenario()
    {
        RacePrice racePrice = new RacePrice(new BigDecimal(20),new BigDecimal(0),new BigDecimal(0), new BigDecimal(0));

        racePrice = PriceModifier.applyLimit(racePrice, BigDecimal.valueOf(15.60));
        Assertions.assertEquals(BigDecimal.valueOf(15.6).setScale(2),racePrice.getBasePrice());
        Assertions.assertEquals(BigDecimal.valueOf(0).setScale(2),racePrice.getTaxList().get(0));
        Assertions.assertEquals(BigDecimal.valueOf(0).setScale(2),racePrice.getTaxList().get(1));
        Assertions.assertEquals(BigDecimal.valueOf(0).setScale(2),racePrice.getTaxList().get(2));
    }

    @Test
    public void testFifthScenario()
    {
        final RacePrice racePrice = new RacePrice(new BigDecimal(0),new BigDecimal(2),new BigDecimal(2), new BigDecimal(2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> PriceModifier.applyLimit(racePrice, BigDecimal.valueOf(5)));
    }

    @Test
    public void testSixthScenario()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RacePrice(new BigDecimal(10), new BigDecimal(-4)));
    }

    @Test
    public void testSeventhScenario()
    {
        Assertions.assertThrows(IllegalArgumentException.class,() -> new RacePrice(new BigDecimal(-10),new BigDecimal(24)));
    }

    @Test
    public void testEightScenario()
    {
        final RacePrice racePrice = new RacePrice(new BigDecimal(8), new BigDecimal(1), new BigDecimal(1), new BigDecimal(1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> PriceModifier.applyLimit(racePrice, BigDecimal.valueOf(-5)));
    }
}
