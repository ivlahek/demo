package hr.ivlahek.demo.rest;

import hr.ivlahek.demo.config.ApiPageable;
import hr.ivlahek.demo.persistence.entity.BitcoinPrice;
import hr.ivlahek.demo.persistence.repository.BitcoinPriceRepository;
import hr.ivlahek.demo.rest.dto.BitcoinPriceDto;
import hr.ivlahek.demo.rest.dto.BitcoinPriceDtoPage;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
public class BitcoinPriceRestController {

    @Autowired
    private BitcoinPriceRepository bitcoinPriceRepository;

    private static final Logger logger = LoggerFactory.getLogger(BitcoinPriceRestController.class);

    @GetMapping(
            path = "/api/v1/bitcoin-prices/latest",
            produces = "application/json"
    )
    //this normally I would extract to service layer
    public BitcoinPriceDto getLastBitcoinPrice() {
        logger.info("Get last bitcoin price called!");
        final BitcoinPriceDto priceDto = map(bitcoinPriceRepository.findFirstByOrderByDateCreatedDesc().orElse(new BitcoinPrice()));
        logger.info("Returning {}", priceDto);
        return priceDto;
    }

    @GetMapping(
            path = "/api/v1/bitcoin-prices",
            produces = "application/json"
    )
    @ApiPageable
    //this normally I would extract to service layer
    public BitcoinPriceDtoPage getBitcoinPrices(
            @ApiParam(value = "To period of time for which data will be returned! ISO 8061 format is used! Example 2020-06-03T20:00:00.000Z", defaultValue = "2020-06-03T20:00:00.000Z", required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "dateFrom") Date from,
            @ApiParam(value = "To period of time for which data will be returned! ISO 8061 format is used! Example 2020-06-03T22:00:00.000Z", defaultValue = "2020-06-06T20:00:00.000Z", required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "dateTo") Date to,
            Pageable pageable) {

        logger.info("Get last bitcoin price called! From {} - To {}. Page {}", from, to, pageable);
        final Page<BitcoinPrice> page = bitcoinPriceRepository.findByDateCreatedBetween(from, to, pageable);
        final List<BitcoinPriceDto> priceDtoList = page.getContent().stream().map(this::map).collect(Collectors.toList());
        logger.info("Returning #{}", priceDtoList.size());
        return new BitcoinPriceDtoPage(priceDtoList, page.getTotalElements());
    }

    private BitcoinPriceDto map(BitcoinPrice bitcoinPrice) {
        return new BitcoinPriceDto(bitcoinPrice.getId(), bitcoinPrice.getDateCreated(), BigDecimal.valueOf(bitcoinPrice.getPriceInUSD()));
    }
}
