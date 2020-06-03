package hr.ivlahek.demo.persistence.repository;

import hr.ivlahek.demo.persistence.entity.BitcoinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface BitcoinPriceRepository extends JpaRepository<BitcoinPrice, Long> {
    Optional<BitcoinPrice> findFirstByOrderByDateCreatedDesc();

    Page<BitcoinPrice> findByDateCreatedBetween(Date from, Date to, Pageable pageable);
}
