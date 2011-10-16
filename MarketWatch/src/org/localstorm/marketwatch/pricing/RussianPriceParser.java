package org.localstorm.marketwatch.pricing;

import java.math.BigDecimal;

class RussianPriceParser {

    public static BigDecimal parse(String s) throws NumberFormatException {
        try {
            s = s.replaceAll("\\.", "").replaceAll(",", ".").replaceAll("\\s", "").replaceAll("[^\\p{ASCII}]", "");

            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid price: ["+s+"]");
        }
    }
}
