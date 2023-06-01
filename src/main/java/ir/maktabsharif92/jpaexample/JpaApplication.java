package ir.maktabsharif92.jpaexample;

import com.github.javafaker.Faker;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.service.CustomerService;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

import java.time.ZonedDateTime;

public class JpaApplication {

    private static final Faker faker = new Faker();


    public static void main(String[] args) {

        CustomerService customerService = ApplicationContext.getCustomerService();

        Customer save = customerService.save(
                getNewCustomer()
        );

    }

    private static Customer getNewCustomer() {
        return new Customer(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.name().username(),
                faker.internet().password(8, 10),
                ZonedDateTime.now()
        );
    }
}
