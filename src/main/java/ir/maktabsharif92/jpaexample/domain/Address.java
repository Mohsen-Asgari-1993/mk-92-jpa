package ir.maktabsharif92.jpaexample.domain;

import ir.maktabsharif92.jpaexample.base.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Address.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity<Long> {

    public static final String TABLE_NAME = "address";

    private String address;
}
