package module;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RacePrice
{
    private final BigDecimal basePrice;
    private final List<BigDecimal> taxes;
    private final List<BigDecimal> allPricesList;
    private final BigDecimal fullRacePrice;

    public BigDecimal getFullRacePrice()
    {
        return this.fullRacePrice;
    }

    public List<BigDecimal> getTaxList()
    {
        return taxes;
    }

    public BigDecimal getBasePrice()
    {
        return basePrice;
    }

    public List<BigDecimal> getAllPricesList()
    {
        return allPricesList;
    }

    public RacePrice(BigDecimal basePrice, BigDecimal... tax)
    {
        this.basePrice = basePrice;
        this.taxes = Arrays.asList(tax);
        allPricesList = new ArrayList<>();
        allPricesList.add(0,this.basePrice);
        allPricesList.addAll(taxes);
        this.fullRacePrice = allPricesList
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
