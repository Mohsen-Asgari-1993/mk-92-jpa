package ir.maktabsharif92.jpaexample.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDashboardInfo implements Serializable {

    private long activeCustomers;

    private long deActiveCustomers;

    private long legalCustomers;

    private long realCustomers;

}
