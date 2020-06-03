package hr.ivlahek.demo.persistence.repository;

import hr.ivlahek.demo.IntegrationTest;
import hr.ivlahek.demo.persistence.entity.BitcoinPrice;
import hr.ivlahek.demo.persistence.entity.BitcoinPriceBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;


class BitcoinPriceRepositoryTest extends IntegrationTest {

    @Autowired
    private BitcoinPriceRepository bitcoinPriceRepository;
    private BitcoinPrice bitcoinPrice1;
    private BitcoinPrice bitcoinPrice2;
    private BitcoinPrice bitcoinPrice3;

    @BeforeEach
    void setUp() {
        bitcoinPrice1 = BitcoinPriceBuilder.aBitcoinPrice().build();
        bitcoinPriceRepository.save(bitcoinPrice1);
        bitcoinPrice2 = BitcoinPriceBuilder.aBitcoinPrice().withDateCreated(Date.from(Instant.now().minusSeconds(1000))).build();
        bitcoinPriceRepository.save(bitcoinPrice2);
        bitcoinPrice3 = BitcoinPriceBuilder.aBitcoinPrice().withDateCreated(Date.from(Instant.now().minusSeconds(10000))).build();
        bitcoinPriceRepository.save(bitcoinPrice3);
    }

    @Test
    public void should_save() {
        //OPERATE
        final BitcoinPrice bitcoinPrice = bitcoinPriceRepository.save(BitcoinPriceBuilder.aBitcoinPrice().build());

        //CHECK
        assertThat(bitcoinPrice.getId()).isNotNull();
    }

    @Test
    public void should_find_latest_bitcoin_price() {
        //OPERATE
        final Page<BitcoinPrice> page = bitcoinPriceRepository.findByDateCreatedBetween(bitcoinPrice3.getDateCreated(), bitcoinPrice2.getDateCreated(), Pageable.unpaged());

        //CHECK
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(extractProperty("id").from(page.getContent())).containsOnly(bitcoinPrice3.getId(), bitcoinPrice2.getId());
    }

}