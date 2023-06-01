package ir.maktabsharif92.jpaexample.base.repository;

import ir.maktabsharif92.jpaexample.base.domain.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseEntityRepository<T extends BaseEntity<ID>, ID extends Serializable> {

    T save(T t);

    List<T> saveALl(Collection<T> tCollection);

    Optional<T> findById(ID id);

    T getById(ID id);

    void deleteById(ID id);

    List<T> findAll();

    boolean existsById(ID id);

    long count();

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();


}
