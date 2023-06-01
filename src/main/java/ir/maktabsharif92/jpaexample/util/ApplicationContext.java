package ir.maktabsharif92.jpaexample.util;

import ir.maktabsharif92.jpaexample.repository.CustomerRepository;
import ir.maktabsharif92.jpaexample.repository.WalletRepository;
import ir.maktabsharif92.jpaexample.repository.impl.CustomerRepositoryImpl;
import ir.maktabsharif92.jpaexample.repository.impl.WalletRepositoryImpl;
import ir.maktabsharif92.jpaexample.service.CustomerService;
import ir.maktabsharif92.jpaexample.service.WalletService;
import ir.maktabsharif92.jpaexample.service.impl.CustomerServiceImpl;
import ir.maktabsharif92.jpaexample.service.impl.WalletServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ApplicationContext {

    private static final EntityManager em =
            Persistence.createEntityManagerFactory(
                    "default"
            ).createEntityManager();

    private static CustomerRepository customerRepository;

    private static CustomerService customerService;

    private static WalletRepository walletRepository;

    private static WalletService walletService;

    public static CustomerRepository getCustomerRepository() {
        if (customerRepository == null) {
            customerRepository = new CustomerRepositoryImpl(em);
        }
        return customerRepository;
    }

    public static CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = new CustomerServiceImpl(
                    getCustomerRepository(), getWalletService()
            );
        }
        return customerService;
    }

    public static WalletRepository getWalletRepository() {
        if (walletRepository == null) {
            walletRepository = new WalletRepositoryImpl(em);
        }
        return walletRepository;
    }

    public static WalletService getWalletService() {
        if (walletService == null) {
            walletService = new WalletServiceImpl(getWalletRepository());
        }
        return walletService;
    }
}
