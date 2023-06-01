package ir.maktabsharif92.jpaexample;

import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.domain.Wallet;
import ir.maktabsharif92.jpaexample.service.CustomerService;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

import java.util.List;

public class JpaApplication {

    public static void main(String[] args) {

        CustomerService customerService = ApplicationContext.getCustomerService();

        List<Customer> customers = customerService.findAll();

        Customer customer = customers.get(0);

        ApplicationContext.getWalletService().save(
                new Wallet(
                        customer
                )
        );


    }
}
