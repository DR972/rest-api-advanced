package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * The class {@code Tag} represents tag entity.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
@Audited
public class Tag extends BaseEntity<Long> {
    /**
     * Tag name.
     */
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<GiftCertificate> giftCertificates = new ArrayList<>();

    /**
     * The constructor creates a Tag object
     *
     * @param id   long id
     * @param name String name
     */
    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }

    public Tag(long id) {
        super(id);
    }

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
