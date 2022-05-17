package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

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
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
@Audited
public class Tag extends BaseEntity<Long> {
    /**
     * Tag name.
     */
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
                .add("name='" + name + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
