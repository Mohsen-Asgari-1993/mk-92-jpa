package ir.maktabsharif92.jpaexample.domain;

import ir.maktabsharif92.jpaexample.base.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = Wallet.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet extends BaseEntity<Long> {

    public static final String TABLE_NAME = "wallet";

    public static final String TOTAL_AMOUNT = "total_amount";
    public static final String CASH_AMOUNT = "cash_amount";
    public static final String CREDIT_AMOUNT = "credit_amount";
    public static final String CUSTOMER_FK = "customer_id";

    @Column(name = TOTAL_AMOUNT)
    private Long totalAmount = 0L;

    @Column(name = CASH_AMOUNT)
    private Long cashAmount = 0L;

    @Column(name = CREDIT_AMOUNT)
    private Long creditAmount = 0L;

    @OneToOne(optional = false)
    @JoinColumn(name = CUSTOMER_FK)
    private Customer customer;

    public Wallet(Customer customer) {
        this.customer = customer;
    }
}
