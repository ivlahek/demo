package hr.ivlahek.demo.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@Entity(name = "bitcoin_price")
public class BitcoinPrice {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "bitcoin_price_seq")
    @SequenceGenerator(name = "bitcoin_price_seq", sequenceName = "bitcoin_price_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "price_in_usd")
    private double priceInUSD;

    public BitcoinPrice(Date dateCreated, double priceInUSD) {
        this.dateCreated = dateCreated;
        this.priceInUSD = priceInUSD;
    }
}
