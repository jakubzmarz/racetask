package test;

import module.PriceModifier;
import module.RacePrice;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class RacePriceTest
{

    @Test
    public void testFirstScenario()
    {
        RacePrice racePrice = new RacePrice(new BigDecimal(10),new BigDecimal(5),new BigDecimal(5));
        PriceModifier priceModifier = new PriceModifier(racePrice,new BigDecimal(10));
        racePrice = priceModifier.applyLimit();
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
        PriceModifier priceModifier = new PriceModifier(racePrice,new BigDecimal(6));
        racePrice = priceModifier.applyLimit();
        Assertions.assertEquals(BigDecimal.valueOf(4.5).setScale(2),racePrice.getBasePrice());
        Assertions.assertEquals(BigDecimal.valueOf(0.5).setScale(2),racePrice.getTaxList().get(0));
        Assertions.assertEquals(BigDecimal.valueOf(0.5).setScale(2),racePrice.getTaxList().get(1));
        Assertions.assertEquals(BigDecimal.valueOf(0.5).setScale(2),racePrice.getTaxList().get(2));
    }
    /*static Stream<Arguments> loadPrices()
    {
        return Stream.of(
                Arguments.arguments(BigDecimal.valueOf(10),BigDecimal.valueOf(5), BigDecimal.valueOf(5),
                        BigDecimal.valueOf(10),
                        BigDecimal.valueOf(5), BigDecimal.valueOf(2.5), BigDecimal.valueOf(2.5)),
                Arguments.arguments(BigDecimal.valueOf(9),BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1),
                        BigDecimal.valueOf(6),
                        BigDecimal.valueOf(4.5), BigDecimal.valueOf(2.5), BigDecimal.valueOf(2.5))
        )
    }*/
}
