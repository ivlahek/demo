package hr.ivlahek.demo.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BitcoinPriceScheduler {

    @Autowired
    private BitcoinPriceImporter bitcoinPriceImporter;

    private static final Logger logger = LoggerFactory.getLogger(BitcoinPriceScheduler.class);

    @Scheduled(fixedDelayString = "${scheduler.fixed.delay.milliseconds:10000}", initialDelayString = "${scheduler.init.delay.milliseconds:10000}")
    public void fetchData() {
        logger.info("Bitcoin price scheduler is awake. Calling bitcoin price importer to import some new prices!");
        bitcoinPriceImporter.importFromExternalSource();
    }

}
