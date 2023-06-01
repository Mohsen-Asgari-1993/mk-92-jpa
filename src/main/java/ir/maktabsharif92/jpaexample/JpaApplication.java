package ir.maktabsharif92.jpaexample;

import com.github.javafaker.Faker;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;
import ir.maktabsharif92.jpaexample.service.CustomerService;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

import java.time.ZonedDateTime;

public class JpaApplication {

    private static final Faker faker = new Faker();


    public static void main(String[] args) {

        CustomerService customerService = ApplicationContext.getCustomerService();

        /*System.out.println(
                "count: " + customerService.count()
        );
        System.out.println();

        System.out.println(
                "count findAll: " + customerService.findAll().size()
        );
        System.out.println();

        Page<Customer> page = customerService.findAll(
                new Pageable(10, 0)
        );

        long totalElements = page.getTotalElements();
        System.out.println(
                "totalElements: " + totalElements
        );
        System.out.println(
                "page 0: "
        );
        page.getContent().forEach(System.out::println);
        System.out.println("-------------");


        System.out.println(
                "page 1: "
        );

        customerService.findAll(
                new Pageable(10, 1)
        ).getContent().forEach(System.out::println);*/

        System.out.println();
        Page<Customer> firstNamePage = customerService
                .findAllByFirstNameContaining(
                        "e",
                        new Pageable(10, 5)
                );
        System.out.println(
                "page by firstName total elements: " + firstNamePage.getTotalElements()
        );
        firstNamePage.getContent().forEach(System.out::println);

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
