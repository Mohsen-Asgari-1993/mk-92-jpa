package ir.maktabsharif92.jpaexample;

import com.github.javafaker.Faker;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.domain.enumeration.CustomerType;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.stream.IntStream;

public class JpaApplication {

    private static final Faker faker = new Faker();


    public static void main(String[] args) {

//        List<Customer> all = ApplicationContext.getCustomerService().findAll();
        Page<Customer> page = ApplicationContext.getCustomerService().findAll(
                new Pageable(10, 1)
        );
        System.out.println(
                page.getTotalElements()
        );
        System.out.println(
                page.getContent().size()
        );

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
