package hr.ivlahek.demo.scheduled;

import hr.ivlahek.demo.external.BitcoinPriceExternalRepository;
import hr.ivlahek.demo.external.dto.BlockChainBitcoinPriceDto;
import hr.ivlahek.demo.persistence.entity.BitcoinPrice;
import hr.ivlahek.demo.persistence.repository.BitcoinPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;

@Service
public class BitcoinPriceImporter {
    @Autowired
    private BitcoinPriceExternalRepository bitcoinPriceExternalRepository;
    @Autowired
    private BitcoinPriceRepository bitcoinPriceRepository;

    private static final Logger logger = LoggerFactory.getLogger(BitcoinPriceImporter.class);

    public void importFromExternalSource() {
        logger.info("Getting usd bitcoin price from an external source!");
        final BlockChainBitcoinPriceDto usdBitcoinPrice = bitcoinPriceExternalRepository.getUsdBitcoinPrice();
        logger.info("Bitcoin price in usd fetched {}", usdBitcoinPrice);
        final BitcoinPrice bitcoinPrice = bitcoinPriceRepository.save(new BitcoinPrice(Date.from(Instant.now()), usdBitcoinPrice.getPriceLast15Minutes().doubleValue()));
        logger.info("Bitcoin price in usd saved to database {}", bitcoinPrice);
    }
}
