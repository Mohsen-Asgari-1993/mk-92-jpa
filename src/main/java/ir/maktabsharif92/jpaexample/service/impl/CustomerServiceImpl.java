package ir.maktabsharif92.jpaexample.service.impl;

import ir.maktabsharif92.jpaexample.base.service.BaseEntityServiceImpl;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.repository.CustomerRepository;
import ir.maktabsharif92.jpaexample.service.CustomerService;

public class CustomerServiceImpl
        extends BaseEntityServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {

    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository);
    }
}
