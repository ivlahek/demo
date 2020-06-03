package hr.ivlahek.demo.external;

import hr.ivlahek.demo.DemoApplication;
import hr.ivlahek.demo.external.dto.BlockChainBitcoinPriceDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class)
class BitcoinPriceExternalRepositoryTest {

    @Autowired
    private BitcoinPriceExternalRepository bitcoinPriceExternalRepository;


    @Test
    public void should_get_bitcoin_price_in_dollars() {
        //OPERATE
        final BlockChainBitcoinPriceDto usdBitcoinPrice = bitcoinPriceExternalRepository.getUsdBitcoinPrice();

        //CHECK
        assertThat(usdBitcoinPrice).isNotNull();
        assertThat(usdBitcoinPrice.getBuy()).isNotZero();
        assertThat(usdBitcoinPrice.getLast()).isNotZero();
        assertThat(usdBitcoinPrice.getPriceLast15Minutes()).isNotZero();
        assertThat(usdBitcoinPrice.getSymbol()).isEqualTo("$");
    }
}