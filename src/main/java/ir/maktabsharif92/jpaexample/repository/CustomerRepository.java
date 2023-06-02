package ir.maktabsharif92.jpaexample.repository;

import ir.maktabsharif92.jpaexample.base.repository.BaseEntityRepository;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.dto.CustomerSearch;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;

import java.util.List;

public interface CustomerRepository
        extends BaseEntityRepository<Customer, Long> {

    Page<Customer> findAllByFirstNameContaining(String firstName, Pageable pageable);

    long countAllActive();

    long countAllDeActive();

    long countAllLegalActive();

    long countAllRealActive();

    List<Customer> findAllWithSearch(CustomerSearch customerSearch);
}
