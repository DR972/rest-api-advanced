package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "customer")
@Audited
@AllArgsConstructor
public class Customer extends BaseEntity<Long> {
    /**
     * Username.
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("customerOrders=" + customerOrders)
                .toString();
    }
}
