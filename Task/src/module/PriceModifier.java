package module;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PriceModifier
{
    private RacePrice racePrice;
    private List<BigDecimal> priceProportionsList = new ArrayList<>();
    private List<BigDecimal> allPricesList;
    private BigDecimal priceLimit;

    public PriceModifier(RacePrice racePrice, BigDecimal priceLimit)
    {
        this.racePrice = racePrice;
        this.priceLimit = priceLimit;
        this.allPricesList = racePrice.getAllPricesList();
    }



    private List<BigDecimal> calculatePriceProportion()
    {
        for(int i = 0; i < allPricesList.size(); i++)
        {
            priceProportionsList
                    .add(i, allPricesList.get(i)
                            .divide(racePrice.getFullRacePrice(), 2, RoundingMode.HALF_DOWN));
        }
        return priceProportionsList;
    }

    private List<BigDecimal> getCalculatedFinalPrices()
    {
        return calculatePriceProportion()
                .stream()
                .map(t -> t
                        .multiply(priceLimit)
                        .setScale(2, RoundingMode.HALF_DOWN))
                .collect(Collectors.toList());
    }

    public BigDecimal getFinalPrice()
    {
        List<BigDecimal> pricesList = getCalculatedFinalPrices();
        BigDecimal finalPrice = pricesList.stream().reduce(BigDecimal.ZERO,BigDecimal::add);
        if(finalPrice.subtract(priceLimit).compareTo(BigDecimal.ZERO) != 0)
            pricesList.set(0,pricesList.get(0).subtract(finalPrice.subtract(priceLimit)));
        return pricesList.stream().reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
