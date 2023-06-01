package ir.maktabsharif92.jpaexample.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityTransaction;

@Setter
@Getter
@AllArgsConstructor
public class CustomEntityTransaction {

    private EntityTransaction entityTransaction;

    private boolean commit;

}
