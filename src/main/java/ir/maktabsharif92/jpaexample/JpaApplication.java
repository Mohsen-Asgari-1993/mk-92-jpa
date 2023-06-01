package ir.maktabsharif92.jpaexample;

import com.github.javafaker.Faker;
import ir.maktabsharif92.jpaexample.dto.CustomerDashboardInfo;
import ir.maktabsharif92.jpaexample.service.CustomerService;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

import java.time.LocalDateTime;

public class JpaApplication {

    private static final Faker faker = new Faker();


    public static void main(String[] args) {

        CustomerService customerService = ApplicationContext.getCustomerService();

        System.out.println("start: " + LocalDateTime.now());
        CustomerDashboardInfo dashboardInfo = customerService.getDashboardInfo();
        System.out.println(
                dashboardInfo
        );
        System.out.println("end " + LocalDateTime.now());

    }

}
