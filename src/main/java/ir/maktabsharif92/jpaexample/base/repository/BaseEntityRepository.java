package ir.maktabsharif92.jpaexample.base.repository;

import ir.maktabsharif92.jpaexample.base.domain.BaseEntity;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;

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

    Page<T> findAll(Pageable pageable);

    boolean existsById(ID id);

    long count();

    void beginTransaction();

    void beginTransaction(boolean commit);

    void commitTransaction();

    void customCommitTransaction();

    void changeCommitStatus(boolean commit);

    void rollbackTransaction();

    void customRollbackTransaction();
}
