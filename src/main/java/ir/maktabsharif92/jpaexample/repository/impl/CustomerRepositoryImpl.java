package ir.maktabsharif92.jpaexample.repository.impl;

import ir.maktabsharif92.jpaexample.base.repository.impl.BaseEntityRepositoryImpl;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.domain.enumeration.CustomerType;
import ir.maktabsharif92.jpaexample.dto.CustomerSearch;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;
import ir.maktabsharif92.jpaexample.repository.CustomerRepository;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    public Page<Customer> findAll(Pageable pageable) {

        return getCustomerPageWithoutFirstResultMaxResultWarning(pageable);
    }

    private Page<Customer> getCustomerPageWithFirstResultMaxResultWarning(Pageable pageable) {
        TypedQuery<Customer> typedQuery = em
                .createQuery(
                        "from " + getEntityClass().getSimpleName(),
                        getEntityClass()
                );
        typedQuery.setMaxResults(
                pageable.getSize()
        );

        int firstResult = pageable.getPage() * pageable.getSize();
        typedQuery.setFirstResult(firstResult);

        EntityGraph<?> entityGraph = em.createEntityGraph(Customer.class);
        entityGraph.addAttributeNodes("addresses");
        typedQuery.setHint(
                "javax.persistence.fetchgraph",
                entityGraph
        );

        List<Customer> content = typedQuery.getResultList();
        return new Page<>(
                content, count()
        );
    }

    private Page<Customer> getCustomerPageWithoutFirstResultMaxResultWarning(Pageable pageable) {
        TypedQuery<Long> typedQuery = em.createQuery(
                "select c.id from Customer c", Long.class
        );
        typedQuery.setMaxResults(
                pageable.getSize()
        );
        int firstResult = pageable.getPage() * pageable.getSize();
        typedQuery.setFirstResult(firstResult);
        List<Long> customerIds = typedQuery.getResultList();
        TypedQuery<Customer> customerQuery = em.createQuery(
                "select c from Customer c where c.id in :ids",
                getEntityClass()
        );
        customerQuery.setParameter("ids", customerIds);
        EntityGraph<?> entityGraph = em.createEntityGraph(Customer.class);
        entityGraph.addAttributeNodes("addresses");
        customerQuery.setHint(
                "javax.persistence.fetchgraph",
                entityGraph
        );

        return new Page<>(
                customerQuery.getResultList(), count()
        );

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

    @Override
    public List<Customer> findAllWithSearch(CustomerSearch customerSearch) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
//        select c from Customer c   -> cb.createQuery(Customer.class)
//                  cb.createQuery(Long.class)       query.from(Customer.class)
//        select    c.id                        from Customer c   -> cb.createQuery(Long.class)
//        select c.firstName from Customer c   -> cb.createQuery(String.class)
//        select c.id from Long c   -> cb.createQuery(String.class)
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> customerRoot = query.from(Customer.class);
        query.select(customerRoot);
        List<Predicate> predicates = new ArrayList<>();
        addFirstNamePredicate(
                predicates, cb, customerRoot, customerSearch.getFirstName()
        );
        addLastNamePredicate(
                predicates, cb, customerRoot, customerSearch.getLastName()
        );
        addUsernamePredicate(
                predicates, cb, customerRoot, customerSearch.getUsername()
        );
        fillFromCreateDatePredicate(
                predicates, cb, customerRoot, customerSearch.getFromCreateDate()
        );
        fillToCreateDatePredicate(
                predicates, cb, customerRoot, customerSearch.getToCreateDate()
        );
        fillIsActivePredicate(
                predicates, cb, customerRoot, customerSearch.getIsActive()
        );
        fillCustomerTypePredicate(
                predicates, cb, customerRoot, customerSearch.getCustomerType()
        );

        query.where(
                cb.and(
                        predicates.toArray(
                                new Predicate[0]
                        )
                )
        );

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<String> findAllFirstNames(CustomerSearch customerSearch) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
//        select c from Customer c   -> cb.createQuery(Customer.class)
//        select c.id from Customer c   -> cb.createQuery(Long.class)
//        select c.firstName from Customer c   -> cb.createQuery(String.class)
//        select c.id from Long c   -> cb.createQuery(String.class)
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<Customer> customerRoot = query.from(Customer.class);
        query.select(customerRoot.get("firstName"));
        List<Predicate> predicates = new ArrayList<>();
        addFirstNamePredicate(
                predicates, cb, customerRoot, customerSearch.getFirstName()
        );
        addLastNamePredicate(
                predicates, cb, customerRoot, customerSearch.getLastName()
        );
        addUsernamePredicate(
                predicates, cb, customerRoot, customerSearch.getUsername()
        );
        fillFromCreateDatePredicate(
                predicates, cb, customerRoot, customerSearch.getFromCreateDate()
        );
        fillToCreateDatePredicate(
                predicates, cb, customerRoot, customerSearch.getToCreateDate()
        );
        fillIsActivePredicate(
                predicates, cb, customerRoot, customerSearch.getIsActive()
        );
        fillCustomerTypePredicate(
                predicates, cb, customerRoot, customerSearch.getCustomerType()
        );

        query.where(
                cb.and(
                        predicates.toArray(
                                new Predicate[0]
                        )
                )
        );

        return em.createQuery(query).getResultList();
    }

    private void addFirstNamePredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                       Root<Customer> root, String firstName) {
//        if (firstName != null && !firstName.isEmpty()) {
        if (StringUtils.isNotBlank(firstName)) {
//                 1       2    3
//            c.firstName like ''
//            c.isActive like ''
//            c.username like ''
            addLikePredicate(predicates, cb, root, firstName, "firstName");
        }
    }

    private void addLastNamePredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                      Root<Customer> root, String lastName) {
        if (StringUtils.isNotBlank(lastName)) {
            addLikePredicate(predicates, cb, root, lastName, "lastName");
        }
    }

    private void addUsernamePredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                      Root<Customer> root, String username) {
        if (StringUtils.isNotBlank(username)) {
            addLikePredicate(predicates, cb, root, username, "username");
        }
    }

    private void fillFromCreateDatePredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                             Root<Customer> customerRoot, ZonedDateTime fromCreateDate) {
        if (fromCreateDate != null) {
            predicates.add(
                    cb.greaterThanOrEqualTo(
                            customerRoot.get("createDate"), fromCreateDate
                    )
            );
        }
    }

    private void fillToCreateDatePredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                           Root<Customer> customerRoot, ZonedDateTime toCreateDate) {
        if (toCreateDate != null) {
            predicates.add(
                    cb.lessThanOrEqualTo(
                            customerRoot.get("createDate"), toCreateDate
                    )
            );
        }
    }

    private void fillIsActivePredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                       Root<Customer> customerRoot, Boolean isActive) {
        if (isActive != null) {
            Predicate predicate;
            if (isActive) {
                predicate = cb.isTrue(
                        customerRoot.get("isActive")
                );
            } else {
                predicate = cb.or(
                        cb.isFalse(
                                customerRoot.get("isActive")
                        ),
                        cb.isNull(
                                customerRoot.get("isActive")
                        )
                );
            }
            predicates.add(predicate);
        }
    }

    private void fillCustomerTypePredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                           Root<Customer> customerRoot, CustomerType customerType) {
        if (customerType != null) {
            predicates.add(
                    cb.equal(
                            customerRoot.get("customerType"), customerType
                    )
            );
        }
    }

    private void addLikePredicate(List<Predicate> predicates, CriteriaBuilder cb, Root<Customer> root,
                                  String fieldValue, String fieldName) {
        predicates.add(
                cb.like(
                        root.get(fieldName), "%" + fieldValue + "%"
                )
        );
    }

    private long countAllBy(String query) {
        return em.createQuery(query, Long.class).getSingleResult();
    }
}
