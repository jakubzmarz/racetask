package module;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PriceModifier
{
    private static BigDecimal getFullRacePrice(RacePrice rp)
    {
        return rp.getTaxList()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(rp.getBasePrice());
    }

    private static List<BigDecimal> loadPricesToList(RacePrice rp)
    {
        List<BigDecimal> allPricesList = new ArrayList<>();
        allPricesList.add(0,rp.getBasePrice());
        allPricesList.addAll(rp.getTaxList());
        return allPricesList;
    }

    private static List<BigDecimal> calculatePriceProportion(RacePrice rp)
    {
        List<BigDecimal> priceProportionsList = new ArrayList<>();
        List<BigDecimal> allPricesList = loadPricesToList(rp);
        for(int i = 0; i < allPricesList.size(); i++)
        {
            priceProportionsList
                    .add(i, allPricesList
                            .get(i)
                            .divide(getFullRacePrice(rp), 3, RoundingMode.HALF_UP));
        }
        return priceProportionsList;
    }

    private static List<BigDecimal> getCalculatedFinalPrices(RacePrice rp, BigDecimal limit)
    {
        return calculatePriceProportion(rp)
                .stream()
                .map(t -> t
                        .multiply(limit)
                        .setScale(2, RoundingMode.HALF_UP))
                .collect(Collectors.toList());
    }

    public static RacePrice applyLimit(RacePrice rp, BigDecimal limit)
    {
        List<BigDecimal> pricesList = getCalculatedFinalPrices(rp,limit);
        BigDecimal finalPrice = pricesList.stream().reduce(BigDecimal.ZERO,BigDecimal::add);

        if(finalPrice.subtract(limit).compareTo(BigDecimal.ZERO) != 0)
            pricesList.set(0,pricesList.get(0).subtract(finalPrice.subtract(limit)));

        BigDecimal finalBasePrice = pricesList.remove(0);
        BigDecimal[] taxes = new BigDecimal[pricesList.size()];

        return new RacePrice(finalBasePrice, pricesList.toArray(taxes));
    }
}
