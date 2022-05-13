package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "customer")
@Audited
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity<Long> {
    /**
     * Username.
     */
    @Column(name = "name")
    private String name;
    /**
     * List<CustomerOrder> customerOrder.
     */
    @OneToMany(mappedBy = "customer")
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    public Customer(long id, String name, List<CustomerOrder> customerOrders) {
        super(id);
        this.name = name;
        this.customerOrders = customerOrders;
    }

    public Customer(long id) {
        super(id);
    }
}
