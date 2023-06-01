package ir.maktabsharif92.jpaexample.repository.impl;

import ir.maktabsharif92.jpaexample.base.repository.impl.BaseEntityRepositoryImpl;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;
import ir.maktabsharif92.jpaexample.repository.CustomerRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Random;

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
    public List<Customer> findAll() {
        EntityGraph<?> entityGraph = em.createEntityGraph(Customer.class);
        entityGraph.addAttributeNodes("addresses");

        TypedQuery<Customer> typedQuery = em.createQuery("select w from Customer w", getEntityClass());

        typedQuery.setHint(
                "javax.persistence.fetchgraph",
                entityGraph
        );

        return typedQuery.getResultList();
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

    @Override
    public long countAllActive() {
        try {
            int waitTime = new Random().nextInt(5000);
            System.out.println("countAllActive waitTime: " + waitTime);
            Thread.sleep(waitTime);
        } catch (Exception ignore) {
        }
        return countAllBy(
                "select count(c) from Customer c where c.isActive = true"
        );
    }

    @Override
    public long countAllDeActive() {
        try {
            int waitTime = new Random().nextInt(5000);
            System.out.println("countAllDeActive waitTime: " + waitTime);
            Thread.sleep(waitTime);
        } catch (Exception ignore) {
        }
        return countAllBy(
                "select count(c) from Customer c where c.isActive = false"
        );
    }

    @Override
    public long countAllLegalActive() {
        try {
            int waitTime = new Random().nextInt(5000);
            System.out.println("countAllLegalActive waitTime: " + waitTime);
            Thread.sleep(waitTime);
        } catch (Exception ignore) {
        }
        return countAllBy(
                "select count(c) from Customer c where c.customerType = 'LEGAL'"
        );
    }

    @Override
    public long countAllRealActive() {
        try {
            int waitTime = new Random().nextInt(5000);
            System.out.println("countAllRealActive waitTime: " + waitTime);
            Thread.sleep(waitTime);
        } catch (Exception ignore) {
        }
        return countAllBy(
                "select count(c) from Customer c where c.customerType = 'REAL'"
        );
    }

    private long countAllBy(String query) {
        return em.createQuery(query, Long.class).getSingleResult();
    }
}
