package ir.maktabsharif92.jpaexample.base.service;

import ir.maktabsharif92.jpaexample.base.domain.BaseEntity;
import ir.maktabsharif92.jpaexample.base.repository.BaseEntityRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BaseEntityServiceImpl<T extends BaseEntity<ID>, ID extends Serializable, R extends BaseEntityRepository<T, ID>>
        implements BaseEntityService<T, ID> {

    private final R repository;

    public BaseEntityServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public T save(T t) {
        try {
            repository.beginTransaction();
            t = repository.save(t);
            repository.commitTransaction();
            return t;
        } catch (Exception e) {
            repository.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public List<T> saveALl(Collection<T> tCollection) {
        try {
            repository.beginTransaction();
            List<T> entities = repository.saveALl(tCollection);
            repository.commitTransaction();
            return entities;
        } catch (Exception e) {
            repository.rollbackTransaction();
            throw e;
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public T getById(ID id) {
        return repository.getById(id);
    }

    @Override
    public void deleteById(ID id) {
        repository.beginTransaction();
        repository.deleteById(id);
        repository.commitTransaction();
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
