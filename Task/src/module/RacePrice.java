package module;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RacePrice
{
    private final BigDecimal basePrice;
    private final List<BigDecimal> taxes;

    public List<BigDecimal> getTaxList()
    {
        return taxes;
    }

    public BigDecimal getBasePrice()
    {
        return basePrice;
    }


    public RacePrice(BigDecimal basePrice, BigDecimal... tax)
    {
        this.basePrice = basePrice;
        this.taxes = Arrays.asList(tax);
    }

}
