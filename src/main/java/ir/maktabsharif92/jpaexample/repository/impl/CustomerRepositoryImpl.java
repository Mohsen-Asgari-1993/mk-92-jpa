package ir.maktabsharif92.jpaexample.repository.impl;

import ir.maktabsharif92.jpaexample.base.repository.impl.BaseEntityRepositoryImpl;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;
import ir.maktabsharif92.jpaexample.repository.CustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CustomerRepositoryImpl
        extends BaseEntityRepositoryImpl<Customer, Long>
        implements CustomerRepository {

    public CustomerRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }

    @Override
    public Page<Customer> findAllByFirstNameContaining(String firstName, Pageable pageable) {
        String fromClause = "from Customer c where c.firstName like :firstName";
        String query = "select c " + fromClause;

        TypedQuery<Customer> typedQuery = em.createQuery(query, getEntityClass());
        typedQuery.setParameter(
                "firstName", "%" + firstName + "%"
        );
        typedQuery.setMaxResults(
                pageable.getSize()
        );

        int firstResult = pageable.getPage() * pageable.getSize();
        typedQuery.setFirstResult(firstResult);
        return new Page<>(
                typedQuery.getResultList(),
                em.createQuery(
                        "select count(c) " + fromClause, Long.class
                ).setParameter("firstName", "%" + firstName + "%").getSingleResult()
        );
    }
}
