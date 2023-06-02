package ir.maktabsharif92.jpaexample.dto;

import ir.maktabsharif92.jpaexample.domain.enumeration.CustomerType;
import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerSearch implements Serializable {

    private String firstName;

    private String lastName;

    private String username;

    private ZonedDateTime fromCreateDate;

    private ZonedDateTime toCreateDate;

    private Boolean isActive;

    private CustomerType customerType;

}
