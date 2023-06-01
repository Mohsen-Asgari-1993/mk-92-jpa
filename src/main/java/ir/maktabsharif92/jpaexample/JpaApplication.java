package ir.maktabsharif92.jpaexample;

import com.github.javafaker.Faker;
import ir.maktabsharif92.jpaexample.domain.Wallet;
import ir.maktabsharif92.jpaexample.service.WalletService;
import ir.maktabsharif92.jpaexample.util.ApplicationContext;

import java.util.List;

public class JpaApplication {

    private static final Faker faker = new Faker();


    public static void main(String[] args) {

        WalletService walletService = ApplicationContext.getWalletService();
        List<Wallet> wallets = walletService.findAll();

        wallets.forEach(
                wallet -> System.out.println(
                        wallet.getCustomer().getFirstName() + " " +
                                wallet.getCustomer().getLastName() +
                                " - addressSize: " + wallet.getCustomer().getAddresses().size()
                )
        );

//        List<Customer> all = ApplicationContext.getCustomerService().findAll();

    }


}
