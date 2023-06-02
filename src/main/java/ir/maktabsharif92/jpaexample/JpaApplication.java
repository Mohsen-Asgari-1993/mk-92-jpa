package ir.maktabsharif92.jpaexample;

import com.github.javafaker.Faker;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.domain.enumeration.CustomerType;
import ir.maktabsharif92.jpaexample.repository.CustomerRepository;
import ir.maktabsharif92.jpaexample.repository.WalletRepository;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.stream.IntStream;

public class JpaApplication {

    private static final Faker faker = new Faker();


    public static void main(String[] args) {

        CustomerRepository customerRepository = ApplicationContext.getCustomerRepository();
        WalletRepository walletRepository = ApplicationContext.getWalletRepository();


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
