package ir.maktabsharif92.jpaexample;

import com.github.javafaker.Faker;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

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


}
