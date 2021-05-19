package module;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public final class RacePrice
{
    private final BigDecimal basePrice;
    private final List<BigDecimal> taxes;

    public RacePrice(BigDecimal basePrice, BigDecimal... tax)
    {
        if(basePrice.compareTo(BigDecimal.ZERO) < 0
                || !Arrays.stream(tax).filter(x -> x.compareTo(BigDecimal.ZERO) < 0).findAny().isEmpty())
            throw new IllegalArgumentException("Values have to be greater than 0");
        this.basePrice = basePrice;
        this.taxes = Arrays.asList(tax);
    }

    public List<BigDecimal> getTaxList()
    {
        return taxes;
    }

    public BigDecimal getBasePrice()
    {
        return basePrice;
    }
}
