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

/**
 * The class {@code Customer} represents Customer entity.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "customer")
@Audited
@AllArgsConstructor
public class Customer extends BaseEntity<Long> {
    /**
     * String name.
     */
    private String name;

    /**
     * List<CustomerOrder> customerOrder.
     */
    @OneToMany(mappedBy = "customerId")
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    /**
     * The constructor creates a Customer object
     *
     * @param id             long id
     * @param name           String name
     * @param customerOrders List<CustomerOrder> customerOrders
     */
    public Customer(long id, String name, List<CustomerOrder> customerOrders) {
        super(id);
        this.name = name;
        this.customerOrders = customerOrders;
    }

    /**
     * The constructor creates a Customer object
     *
     * @param id long id
     */
    public Customer(long id) {
        super(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCustomerOrders() != null ? getCustomerOrders().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("customerOrders=" + customerOrders)
                .toString();
    }
}
