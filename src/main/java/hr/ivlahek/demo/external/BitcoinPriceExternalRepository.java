package hr.ivlahek.demo.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.ivlahek.demo.external.dto.BlockChainBitcoinPriceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class BitcoinPriceExternalRepository {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${external.api.url:https://blockchain.info/ticker}")
    private String externalApiUrl;

    private static final Logger logger = LoggerFactory.getLogger(BitcoinPriceExternalRepository.class);

    @PostConstruct
    public void setUp() {
        logger.info("Initializing {} with root URL {}", BitcoinPriceExternalRepository.class, externalApiUrl);
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(externalApiUrl));
    }

    public BlockChainBitcoinPriceDto getUsdBitcoinPrice() {
        logger.info("Getting the usd bitcoin price from {}", externalApiUrl);
        final Map<String, Map> body = restTemplate.getForEntity("", Map.class).getBody();
        logger.debug("Fetched {}", body);
        return objectMapper.convertValue(body.get("USD"), BlockChainBitcoinPriceDto.class);
    }

}
