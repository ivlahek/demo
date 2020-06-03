package hr.ivlahek.demo.rest;

import hr.ivlahek.demo.WebApiTest;
import hr.ivlahek.demo.persistence.entity.BitcoinPrice;
import hr.ivlahek.demo.persistence.entity.BitcoinPriceBuilder;
import hr.ivlahek.demo.rest.dto.BitcoinPriceDto;
import hr.ivlahek.demo.rest.dto.BitcoinPriceDtoPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

class BitcoinPriceRestControllerTest extends WebApiTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    SimpleDateFormat simpleDateFormat;

    private BitcoinPrice bitcoinPrice1;
    private BitcoinPrice bitcoinPrice2;
    private BitcoinPrice bitcoinPrice3;
    private Map<String, Object> parameters;

    @BeforeEach
    void setUp() {
        bitcoinPrice1 = BitcoinPriceBuilder.aBitcoinPrice()
                .withPriceInUSD(1d).build();
        bitcoinPriceRepository.save(bitcoinPrice1);
        bitcoinPrice2 = BitcoinPriceBuilder.aBitcoinPrice()
                .withPriceInUSD(2d)
                .withDateCreated(Date.from(Instant.now().minusSeconds(1000)))
                .build();
        bitcoinPriceRepository.save(bitcoinPrice2);
        bitcoinPrice3 = BitcoinPriceBuilder.aBitcoinPrice()
                .withPriceInUSD(3d)
                .withDateCreated(Date.from(Instant.now().minusSeconds(1000)))
                .build();
        bitcoinPriceRepository.save(bitcoinPrice3);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        parameters = new HashMap<>();
        parameters.put("dateFrom", simpleDateFormat.format(bitcoinPrice2.getDateCreated()));
        parameters.put("dateTo", simpleDateFormat.format(bitcoinPrice3.getDateCreated()));
        parameters.put("page", 0);
        parameters.put("size", 20);
    }

    @Test
    public void should_fetch_last_bit_coin_price() {
        //BUILD

        //OPERATE
        final BitcoinPriceDto bitcoinPriceDto = testRestTemplate.getForEntity("/api/v1/bitcoin-prices/latest", BitcoinPriceDto.class).getBody();

        //CHECK
        assertThat(bitcoinPriceDto.getId()).isEqualTo(bitcoinPrice1.getId());
        assertThat(bitcoinPriceDto.getDateCreated()).isEqualTo(bitcoinPrice1.getDateCreated());
        assertThat(bitcoinPriceDto.getPriceInUSD().doubleValue()).isEqualTo(bitcoinPrice1.getPriceInUSD());
    }

    @Test
    public void should_fetch_coin_price_in_a_period() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dateFrom", simpleDateFormat.format(bitcoinPrice2.getDateCreated()));
        parameters.put("dateTo", simpleDateFormat.format(bitcoinPrice3.getDateCreated()));

        //OPERATE
        final BitcoinPriceDtoPage bitcoinPriceDtoPage = testRestTemplate.getForEntity(buildForUrl("/api/v1/bitcoin-prices", parameters), BitcoinPriceDtoPage.class).getBody();

        //CHECK
        assertThat(bitcoinPriceDtoPage.getSize()).isEqualTo(2);
        assertThat(bitcoinPriceDtoPage.getBitcoinPriceDto()).hasSize(2);
        assertThat(bitcoinPriceDtoPage.getBitcoinPriceDto().get(0).getId()).isEqualTo(bitcoinPrice2.getId());
        assertThat(bitcoinPriceDtoPage.getBitcoinPriceDto().get(1).getId()).isEqualTo(bitcoinPrice3.getId());
    }

    @Test
    public void should_fetch_coin_price_in_a_period_first_page() {
        parameters.put("size", 1);

        //OPERATE
        final BitcoinPriceDtoPage bitcoinPriceDtoPage = testRestTemplate.getForEntity(buildForUrl("/api/v1/bitcoin-prices", parameters), BitcoinPriceDtoPage.class).getBody();

        //CHECK
        assertThat(bitcoinPriceDtoPage.getSize()).isEqualTo(2);
        assertThat(bitcoinPriceDtoPage.getBitcoinPriceDto()).hasSize(1);
        assertThat(bitcoinPriceDtoPage.getBitcoinPriceDto().get(0).getId()).isEqualTo(bitcoinPrice2.getId());
    }

    @Test
    public void should_fetch_coin_price_in_a_period_second_page() {
        parameters.put("size", 1);
        parameters.put("page", 1);

        //OPERATE
        final BitcoinPriceDtoPage bitcoinPriceDtoPage = testRestTemplate.getForEntity(buildForUrl("/api/v1/bitcoin-prices", parameters), BitcoinPriceDtoPage.class).getBody();

        //CHECK
        assertThat(bitcoinPriceDtoPage.getSize()).isEqualTo(2);
        assertThat(bitcoinPriceDtoPage.getBitcoinPriceDto()).hasSize(1);
        assertThat(bitcoinPriceDtoPage.getBitcoinPriceDto().get(0).getId()).isEqualTo(bitcoinPrice3.getId());
    }

    @Test
    public void should_inform_date_to_is_missing() {
        parameters.remove("dateTo");

        //OPERATE
        final HttpStatus statusCode = testRestTemplate.getForEntity(buildForUrl("/api/v1/bitcoin-prices", parameters), String.class).getStatusCode();

        //CHECK
        assertThat(statusCode.is4xxClientError()).isTrue();
    }

    @Test
    public void should_inform_date_from_is_missing() {
        parameters.remove("dateFrom");

        //OPERATE
        final HttpStatus statusCode = testRestTemplate.getForEntity(buildForUrl("/api/v1/bitcoin-prices", parameters), String.class).getStatusCode();

        //CHECK
        assertThat(statusCode.is4xxClientError()).isTrue();
    }

    public static String buildForUrl(String path, Map<String, Object> queryParameters) {
        UriComponentsBuilder uriComponentsBuilder = fromPath(path);
        for (Map.Entry<String, Object> stringObjectEntry : queryParameters.entrySet()) {
            uriComponentsBuilder.queryParam(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return uriComponentsBuilder.build().toString();
    }
}