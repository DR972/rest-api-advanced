package com.epam.esm.dao.impl;

import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.entity.CustomerOrder;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerOrderDaoImpl extends AbstractDao<CustomerOrder, Long> implements CustomerOrderDao {
    /**
     * EntityManager entityManager.
     */
    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * The constructor creates an CustomerOrderDaoImpl object
     */
    protected CustomerOrderDaoImpl() {
        super(CustomerOrder.class);
    }

    @Override
    public Optional<CustomerOrder> findCustomerOrder(long customerId, long orderId) {
        return entityManager.unwrap(Session.class).createQuery("SELECT e FROM CustomerOrder e WHERE e.id = :orderId and e.customerId = :customerId", CustomerOrder.class)
                .setParameter("orderId", orderId)
                .setParameter("customerId", customerId)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public List<CustomerOrder> findCustomerOrderList(long customerId, int offset, int limit) {
        return entityManager.unwrap(Session.class).createQuery("SELECT e FROM CustomerOrder e WHERE e.customerId = :customerId", CustomerOrder.class)
                .setParameter("customerId", customerId)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public long countNumberEntityRowsInListCustomerOrders(long customerId) {
        return entityManager.unwrap(Session.class).createQuery("SELECT e FROM CustomerOrder e WHERE e.customerId = :customerId", CustomerOrder.class)
                .setParameter("customerId", customerId).getResultList().size();
    }
}
