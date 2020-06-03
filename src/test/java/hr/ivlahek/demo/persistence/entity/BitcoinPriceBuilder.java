package hr.ivlahek.demo.persistence.entity;

import java.time.Instant;
import java.util.Date;

public final class BitcoinPriceBuilder {
    private Long id;
    private Date dateCreated = Date.from(Instant.now());
    private double priceInUSD = 1000.20;

    private BitcoinPriceBuilder() {
    }

    public static BitcoinPriceBuilder aBitcoinPrice() {
        return new BitcoinPriceBuilder();
    }

    public BitcoinPriceBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public BitcoinPriceBuilder withDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public BitcoinPriceBuilder withPriceInUSD(double priceInUSD) {
        this.priceInUSD = priceInUSD;
        return this;
    }

    public BitcoinPrice build() {
        BitcoinPrice bitcoinPrice = new BitcoinPrice();
        bitcoinPrice.setId(id);
        bitcoinPrice.setDateCreated(dateCreated);
        bitcoinPrice.setPriceInUSD(priceInUSD);
        return bitcoinPrice;
    }
}
