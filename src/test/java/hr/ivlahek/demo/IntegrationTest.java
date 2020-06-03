package hr.ivlahek.demo;

import hr.ivlahek.demo.persistence.repository.BitcoinPriceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class)
public class IntegrationTest {
    @Autowired
    protected BitcoinPriceRepository bitcoinPriceRepository;

    @AfterEach
    public void tearDown() {
        bitcoinPriceRepository.deleteAll();
    }
}
