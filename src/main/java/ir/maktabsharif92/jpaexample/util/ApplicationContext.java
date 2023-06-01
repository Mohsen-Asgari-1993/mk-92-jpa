package ir.maktabsharif92.jpaexample.util;

import ir.maktabsharif92.jpaexample.repository.CustomerRepository;
import ir.maktabsharif92.jpaexample.repository.impl.CustomerRepositoryImpl;
import ir.maktabsharif92.jpaexample.service.CustomerService;
import ir.maktabsharif92.jpaexample.service.CustomerServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ApplicationContext {

    private static final EntityManager em =
            Persistence.createEntityManagerFactory(
                    "default"
            ).createEntityManager();

    private static CustomerRepository customerRepository;

    private static CustomerService customerService;

    public static CustomerRepository getCustomerRepository() {
        if (customerRepository == null) {
            customerRepository = new CustomerRepositoryImpl(em);
        }
        return customerRepository;
    }

    public static CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = new CustomerServiceImpl(
                    getCustomerRepository()
            );
        }
        return customerService;
    }
}
