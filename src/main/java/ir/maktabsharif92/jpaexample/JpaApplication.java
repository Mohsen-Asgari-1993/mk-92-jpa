package ir.maktabsharif92.jpaexample;

import com.github.javafaker.Faker;
import ir.maktabsharif92.jpaexample.dto.CustomerDashboardInfo;
import ir.maktabsharif92.jpaexample.service.CustomerService;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class JpaApplication {

    private static final Faker faker = new Faker();


    public static void main(String[] args) {

        CustomerService customerService = ApplicationContext.getCustomerService();

        System.out.println("start: " + LocalDateTime.now());
        long startMilli = ZonedDateTime.now().toInstant().toEpochMilli();
        CustomerDashboardInfo dashboardInfo = customerService.getDashboardInfo();
        System.out.println(
                dashboardInfo
        );
        long endMilli = ZonedDateTime.now().toInstant().toEpochMilli();
        System.out.println("end " + LocalDateTime.now());
        System.out.println(endMilli - startMilli);

    }

}
