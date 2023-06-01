package ir.maktabsharif92.jpaexample.domain;

import ir.maktabsharif92.jpaexample.base.domain.BaseEntity;
import ir.maktabsharif92.jpaexample.domain.enumeration.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = Customer.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity<Long> {

    public static final String TABLE_NAME = "base_user";

    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String CREATE_DATE = "CREATE_DATE";
    public static final String IS_ACTIVE = "is_active";
    public static final String CUSTOMER_TYPE = "customer_type";

    @Column(name = FIRST_NAME)
    private String firstName;

    @Column(name = LAST_NAME)
    private String lastName;

    @Column(name = USER_NAME, nullable = false, unique = true)
    private String username;

    @Column(name = PASSWORD)
    private String password;

    @Column(name = CREATE_DATE)
    private ZonedDateTime createDate;

    @Column(name = IS_ACTIVE)
    private Boolean isActive;

    @Column(name = CUSTOMER_TYPE)
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
