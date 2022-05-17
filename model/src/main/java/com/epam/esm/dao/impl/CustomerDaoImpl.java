package com.epam.esm.dao.impl;

import com.epam.esm.dao.CustomerDao;
import com.epam.esm.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDaoImpl extends AbstractDao<Customer, Long> implements CustomerDao {

    /**
     * The constructor creates an CustomerDaoImpl object
     */
    protected CustomerDaoImpl() {
        super(Customer.class);
    }
}
