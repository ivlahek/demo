package hr.ivlahek.demo.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BlockChainBitcoinPriceDto {

    @JsonProperty(value = "15m")
    private BigDecimal priceLast15Minutes;
    @JsonProperty(value = "last")
    private BigDecimal last;
    @JsonProperty(value = "buy")
    private BigDecimal buy;
    @JsonProperty(value = "sell")
    private BigDecimal sell;
    @JsonProperty(value = "symbol")
    private String symbol;
}
