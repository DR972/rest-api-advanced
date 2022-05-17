package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "customer_order")
@Audited
public class CustomerOrder extends BaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerOrder)) return false;

        CustomerOrder customerOrder = (CustomerOrder) o;

        if (!purchaseTime.equals(customerOrder.purchaseTime)) return false;
        if (!giftCertificates.equals(customerOrder.giftCertificates)) return false;
        return amount.equals(customerOrder.amount);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CustomerOrder.class.getSimpleName() + "[", "]")
                .add("customer=" + customer)
                .add("purchaseTime=" + purchaseTime)
                .add("giftCertificates=" + giftCertificates)
                .add("amount=" + amount)
                .toString();
    }
}
