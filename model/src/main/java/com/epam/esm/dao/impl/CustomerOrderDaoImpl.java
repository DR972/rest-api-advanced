package com.epam.esm.dao.impl;

import com.epam.esm.dao.CustomerOrderDao;
import com.epam.esm.entity.CustomerOrder;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerOrderDaoImpl extends AbstractDao<CustomerOrder, Long> implements CustomerOrderDao {
    /**
     * The constructor creates an CustomerOrderDaoImpl object
     */
    public CustomerOrderDaoImpl() {
        super(CustomerOrder.class);
    }

    @Override
    public Optional<CustomerOrder> findCustomerOrder(long customerId, long orderId) {
        return entityManager.unwrap(Session.class).createQuery("SELECT o FROM CustomerOrder o WHERE o.id = :orderId and o.customerId = :customerId", CustomerOrder.class)
                .setParameter("orderId", orderId)
                .setParameter("customerId", customerId)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public Optional<CustomerOrder> findCustomerOrderByCertificateId(long certificateId) {
        return entityManager.unwrap(Session.class).createQuery("SELECT o FROM CustomerOrder o LEFT JOIN o.giftCertificates g WHERE g.id = :certificateId", CustomerOrder.class)
                .setParameter("certificateId", certificateId)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public List<CustomerOrder> findCustomerOrderList(long customerId, int offset, int limit) {
        return entityManager.unwrap(Session.class).createQuery("SELECT o FROM CustomerOrder o WHERE o.customerId = :customerId", CustomerOrder.class)
                .setParameter("customerId", customerId)
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public long countNumberEntityRowsInListCustomerOrders(long customerId) {
        return entityManager.unwrap(Session.class).createQuery("SELECT o FROM CustomerOrder o WHERE o.customerId = :customerId", CustomerOrder.class)
                .setParameter("customerId", customerId).getResultList().size();
    }
}
