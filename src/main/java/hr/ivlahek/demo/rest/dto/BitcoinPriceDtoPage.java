package hr.ivlahek.demo.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BitcoinPriceDtoPage {
    private List<BitcoinPriceDto> bitcoinPriceDto;
    private long size;
}
