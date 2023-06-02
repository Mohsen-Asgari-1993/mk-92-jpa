package ir.maktabsharif92.jpaexample.service;

import ir.maktabsharif92.jpaexample.base.service.BaseEntityService;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.dto.CustomerDashboardInfo;
import ir.maktabsharif92.jpaexample.dto.CustomerSearch;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;

import java.util.List;

public interface CustomerService extends BaseEntityService<Customer, Long> {

    Page<Customer> findAllByFirstNameContaining(String firstName, Pageable pageable);

    CustomerDashboardInfo getDashboardInfo();

    List<Customer> findAllWithSearch(CustomerSearch customerSearch);

    List<String> findAllFirstNames(CustomerSearch customerSearch);
}
