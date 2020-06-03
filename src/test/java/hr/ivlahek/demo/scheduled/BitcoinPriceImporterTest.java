package hr.ivlahek.demo.scheduled;

import hr.ivlahek.demo.IntegrationTest;
import hr.ivlahek.demo.persistence.entity.BitcoinPrice;
import hr.ivlahek.demo.persistence.repository.BitcoinPriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BitcoinPriceImporterTest extends IntegrationTest {

    @Autowired
    private BitcoinPriceImporter bitcoinPriceImporter;



    @Test
    public void should_import_from_external_source() {
        //OPERATE
        bitcoinPriceImporter.importFromExternalSource();

        //CHECK
        final List<BitcoinPrice> prices = bitcoinPriceRepository.findAll();
        assertThat(prices).hasSize(1);
        assertThat(prices.get(0).getDateCreated()).isCloseTo(Date.from(Instant.now()), 500);
    }

}