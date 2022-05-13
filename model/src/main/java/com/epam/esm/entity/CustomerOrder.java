package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "customerOrder")
@Audited
public class CustomerOrder extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "purchase_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime purchaseTime;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "customer_order_gift_certificate",
            joinColumns = @JoinColumn(name = "customer_order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id")
    )
    private List<GiftCertificate> giftCertificates;

    @Column(name = "amount")
    private BigDecimal amount;

    public CustomerOrder(long id, Customer customer, LocalDateTime purchaseTime, List<GiftCertificate> giftCertificates, BigDecimal amount) {
        super(id);
        this.customer = customer;
        this.purchaseTime = purchaseTime;
        this.giftCertificates = giftCertificates;
        this.amount = amount;
    }

    public CustomerOrder(long id) {
        super(id);
    }

    public CustomerOrder(long id, LocalDateTime purchaseTime, ArrayList<GiftCertificate> giftCertificates, BigDecimal amount) {
        super(id);
        this.purchaseTime = purchaseTime;
        this.giftCertificates = giftCertificates;
        this.amount = amount;
    }
}
