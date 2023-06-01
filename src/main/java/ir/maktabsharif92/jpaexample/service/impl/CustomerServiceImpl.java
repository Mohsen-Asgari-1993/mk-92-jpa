package ir.maktabsharif92.jpaexample.service.impl;

import ir.maktabsharif92.jpaexample.base.service.BaseEntityServiceImpl;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.domain.Wallet;
import ir.maktabsharif92.jpaexample.dto.CustomerDashboardInfo;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;
import ir.maktabsharif92.jpaexample.repository.CustomerRepository;
import ir.maktabsharif92.jpaexample.service.CustomerService;
import ir.maktabsharif92.jpaexample.service.WalletService;

public class CustomerServiceImpl
        extends BaseEntityServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {

    private final WalletService walletService;

    public CustomerServiceImpl(CustomerRepository repository, WalletService walletService) {
        super(repository);
        this.walletService = walletService;
    }

    @Override
    public Customer save(Customer customer) {

        if (customer.getId() == null) {
            try {
                repository.beginTransaction(false);
                customer = repository.save(customer);
                walletService.save(
                        new Wallet(
                                customer
                        )
                );
                repository.changeCommitStatus(true);
                repository.customCommitTransaction();
                return customer;
            } catch (Exception e) {
                repository.customRollbackTransaction();
                throw e;
            }
        } else {
            return repository.save(customer);
        }

    }

    @Override
    public Page<Customer> findAllByFirstNameContaining(String firstName, Pageable pageable) {
        return repository.findAllByFirstNameContaining(firstName, pageable);
    }

    @Override
    public CustomerDashboardInfo getDashboardInfo() {
        return new CustomerDashboardInfo(
                repository.countAllActive(),
                repository.countAllDeActive(),
                repository.countAllLegalActive(),
                repository.countAllRealActive()
        );
    }
}
