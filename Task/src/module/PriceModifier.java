package module;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PriceModifier
{
    private RacePrice racePrice;
    private List<BigDecimal> priceProportionsList = new ArrayList<>();
    private List<BigDecimal> allPricesList = new ArrayList<>();
    private BigDecimal priceLimit;

    public PriceModifier(RacePrice racePrice, BigDecimal priceLimit)
    {
        this.racePrice = racePrice;
        this.priceLimit = priceLimit;
        loadPricesToList();
    }

    private BigDecimal getFullRacePrice()
    {
        return racePrice.getTaxList()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(racePrice.getBasePrice());
    }

    private void loadPricesToList()
    {
        allPricesList.add(0,racePrice.getBasePrice());
        allPricesList.addAll(racePrice.getTaxList());
    }

    private List<BigDecimal> calculatePriceProportion()
    {
        for(int i = 0; i < allPricesList.size(); i++)
        {
            priceProportionsList
                    .add(i, allPricesList.get(i)
                            .divide(getFullRacePrice(), 3, RoundingMode.HALF_UP));
        }
        return priceProportionsList;
    }

    private List<BigDecimal> getCalculatedFinalPrices()
    {
        return calculatePriceProportion()
                .stream()
                .map(t -> t
                        .multiply(priceLimit)
                        .setScale(2, RoundingMode.HALF_UP))
                .collect(Collectors.toList());
    }

    public RacePrice applyLimit()
    {
        RacePrice finalRacePrice;
        List<BigDecimal> pricesList = getCalculatedFinalPrices();
        BigDecimal finalPrice = pricesList.stream().reduce(BigDecimal.ZERO,BigDecimal::add);
        if(finalPrice.subtract(priceLimit).compareTo(BigDecimal.ZERO) != 0)
            pricesList.set(0,pricesList.get(0).subtract(finalPrice.subtract(priceLimit)));
        BigDecimal finalBasePrice = pricesList.remove(0);
        BigDecimal[] taxes = new BigDecimal[pricesList.size()-1];
        finalRacePrice = new RacePrice(finalBasePrice, pricesList.toArray(taxes));
        return finalRacePrice;
    }
}
