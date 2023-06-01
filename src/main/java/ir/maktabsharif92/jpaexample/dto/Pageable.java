package ir.maktabsharif92.jpaexample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {

    private int size = 10;

    private int page = 0;

}
