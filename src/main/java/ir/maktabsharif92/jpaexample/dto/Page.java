package ir.maktabsharif92.jpaexample.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Page<T> {

    private List<T> content;

    private long totalElements;
}
