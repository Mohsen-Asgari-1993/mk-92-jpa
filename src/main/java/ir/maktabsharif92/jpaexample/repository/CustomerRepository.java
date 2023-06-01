package ir.maktabsharif92.jpaexample.repository;

import ir.maktabsharif92.jpaexample.base.repository.BaseEntityRepository;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;

public interface CustomerRepository
        extends BaseEntityRepository<Customer, Long> {

    Page<Customer> findAllByFirstNameContaining(String firstName, Pageable pageable);

}
