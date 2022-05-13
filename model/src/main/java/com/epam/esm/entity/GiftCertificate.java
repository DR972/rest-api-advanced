package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The class {@code GiftCertificate} represents GiftCertificate entity.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name = "gift_certificate")
@Audited
public class GiftCertificate extends BaseEntity<Long> {
    /**
     * GiftCertificate name.
     */
    @Column(name = "name")
    private String name;
    /**
     * GiftCertificate description.
     */
    @Column(name = "description")

    private String description;
    /**
     * GiftCertificate price.
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * GiftCertificate duration.
     */
    @Column(name = "duration")
    private Integer duration;

    /**
     * GiftCertificate createDate.
     */
    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    /**
     * GiftCertificate lastUpdateDate.
     */
    @Column(name = "last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    /**
     * List<Tag> tags
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @ManyToMany(mappedBy = "giftCertificates")
    private List<CustomerOrder> customerOrders;

    /**
     * The constructor creates a GiftCertificate object
     *
     * @param id             long id
     * @param name           String name
     * @param description    String description
     * @param price          BigDecimal price
     * @param duration       int duration
     * @param createDate     LocalDateTime createDate
     * @param lastUpdateDate LocalDateTime lastUpdateDate
     * @param tags           List<Tag> tags
     */
    public GiftCertificate(long id, String name, String description, BigDecimal price, int duration, LocalDateTime createDate,
                           LocalDateTime lastUpdateDate, List<Tag> tags) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    /**
     * The constructor creates a GiftCertificate object
     *
     * @param name        String name
     * @param description String description
     * @param price       BigDecimal price
     * @param duration    int duration
     * @param tags        List<Tag> tags
     */
    public GiftCertificate(String name, String description, BigDecimal price, Integer duration, List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public GiftCertificate(long id) {
        super(id);
    }
}
