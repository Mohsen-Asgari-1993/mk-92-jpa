package ir.maktabsharif92.jpaexample;

import com.github.javafaker.Faker;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.domain.enumeration.CustomerType;
import ir.maktabsharif92.jpaexample.dto.CustomerSearch;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class JpaApplication {

    private static final Faker faker = new Faker();


    public static void main(String[] args) {

//        List<Customer> all = ApplicationContext.getCustomerService().findAll();
        List<Customer> customers = ApplicationContext.getCustomerService().findAllWithSearch(
                CustomerSearch.builder()
                        .firstName("on")
                        .lastName("e")
                        .isActive(true)
                        .build()
        );

        System.out.println(
                ApplicationContext.getCustomerService().findAllFirstNames(
                        CustomerSearch.builder()
                                .firstName("on")
                                .lastName("e")
                                .isActive(true)
                                .build()
                )
        );

        System.out.println(customers.size());


    }

    private static void initCustomers() {
        IntStream.range(0, 100)
                .forEach(i -> {
                            ApplicationContext.getCustomerService().save(
                                    getNewCustomer()
                            );
                        }
                );
    }

    private static Customer getNewCustomer() {
        Random random = new Random();
        return Customer.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .username(faker.name().username())
                .password(faker.internet().password(8, 10))
                .createDate(ZonedDateTime.now())
                .isActive(
                        random.nextBoolean()
                )
                .customerType(
                        CustomerType.values()[
                                random.nextInt(2)
                                ]
                )
                .build();
    }


}
