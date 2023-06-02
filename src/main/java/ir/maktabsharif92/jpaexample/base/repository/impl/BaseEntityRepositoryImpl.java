package ir.maktabsharif92.jpaexample.base.repository.impl;

import ir.maktabsharif92.jpaexample.base.domain.BaseEntity;
import ir.maktabsharif92.jpaexample.base.repository.BaseEntityRepository;
import ir.maktabsharif92.jpaexample.dto.Page;
import ir.maktabsharif92.jpaexample.dto.Pageable;
import ir.maktabsharif92.jpaexample.util.CustomEntityTransaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class BaseEntityRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
        implements BaseEntityRepository<T, ID> {

    protected final EntityManager em;

    private CustomEntityTransaction entityTransaction;

    private Class<T> entityClass;

    private Class<ID> idClass;

    public BaseEntityRepositoryImpl(EntityManager em) {
        this.em = em;
//        get entity class with type
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType superclass = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = superclass.getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 1) {
                if (actualTypeArguments[0] instanceof Class) {
                    this.entityClass = (Class<T>) actualTypeArguments[0];
                }
                if (actualTypeArguments[1] instanceof Class) {
                    this.idClass = (Class<ID>) actualTypeArguments[1];
                }
            }
        }
    }

    @Override
    public T save(T t) {
        beginTransaction(true);
        t = saveWithoutTransaction(t);
        customCommitTransaction();
        return t;
    }

    private T saveWithoutTransaction(T t) {
        if (t.getId() == null) {
            em.persist(t);
        } else {
//            em.merge(t);
            t = em.merge(t);
        }
        return t;
    }

    @Override
    public List<T> saveALl(Collection<T> tCollection) {
        beginTransaction();
        List<T> savedEntities = new ArrayList<>();
        tCollection.forEach(
                entity -> savedEntities.add(saveWithoutTransaction(entity))
        );
        commitTransaction();
        return savedEntities;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(
                em.find(
                        getEntityClass(), id
                )
        );
    }

    @Override
    public T getById(ID id) {
        return em.find(
                getEntityClass(), id
        );
    }

    @Override
    public void deleteById(ID id) {
        beginTransaction();

        em.remove(
                findById(id).orElseThrow(
                        () -> new RuntimeException("entity not found")
                )
        );

        /*Query query = em.createQuery(
                "delete from " + getEntityClass().getSimpleName() + " where id = :id"
        );
        query.setParameter("id", id);
        query.executeUpdate();*/

        commitTransaction();
    }

    @Override
    public List<T> findAll() {
//        criteria

//        select e from #entity#
        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<T> query = cb.createQuery(T.class);
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return em.createQuery(query).getResultList();

//        return em.createQuery("from " + getEntityClass().getSimpleName(), getEntityClass())
//                .getResultList();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setMaxResults(
                pageable.getSize()
        );
        typedQuery.setFirstResult(
                pageable.getPage() * pageable.getSize()
        );
        List<T> content = typedQuery.getResultList();
        return new Page<>(
                content, count()
        );


    }

    private Page<T> getPageFirstApproach(Pageable pageable) {
        TypedQuery<T> typedQuery = em
                .createQuery(
                        "from " + getEntityClass().getSimpleName(),
                        getEntityClass()
                );
        typedQuery.setMaxResults(
                pageable.getSize()
        );

//         offset
//                                0                   10
//                                1                   10
        int firstResult = pageable.getPage() * pageable.getSize();
        typedQuery.setFirstResult(firstResult);
        List<T> content = typedQuery.getResultList();
        return new Page<>(
                content, count()
        );
    }

    @Override
    public boolean existsById(ID id) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<T> root = query.from(getEntityClass());

        query.select(
                cb.count(root)
        );
        query.where(
                cb.equal(
                        root.get("id"), id
                )
        );

        return em.createQuery(query).getSingleResult() > 0;

    }

    private boolean existsByIdFirstApproach(ID id) {
        TypedQuery<Long> typedQuery = em.createQuery(
                "select count(e) from " + getEntityClass().getSimpleName() + " e where e.id = :id",
                Long.class
        );
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult() > 0;
    }

    @Override
    public long count() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        Root<T> root = query.from(getEntityClass());

        query.select(
                cb.count(root)
        );

        return em.createQuery(query).getSingleResult();
    }

    private Long countFirstApproach() {
        return em.createQuery(
                "select count(e) from " + getEntityClass().getSimpleName() + " e",
                Long.class
        ).getSingleResult();
    }

    @Override
    public void beginTransaction() {
        EntityTransaction transaction = em.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
    }

    @Override
    public void beginTransaction(boolean commit) {
        EntityTransaction transaction = em.getTransaction();
        if (!transaction.isActive()) {
            this.entityTransaction = new CustomEntityTransaction(
                    transaction, commit
            );
            transaction.begin();
        }
    }

    @Override
    public void commitTransaction() {
        EntityTransaction transaction = em.getTransaction();
        if (transaction.isActive()) {
            transaction.commit();
        }
    }

    @Override
    public void customCommitTransaction() {
        if (this.entityTransaction != null && this.entityTransaction.isCommit()) {
            this.entityTransaction.getEntityTransaction().commit();
        }
    }

    @Override
    public void changeCommitStatus(boolean commit) {
        this.entityTransaction.setCommit(commit);
    }

    @Override
    public void rollbackTransaction() {
        EntityTransaction transaction = em.getTransaction();
        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

    @Override
    public void customRollbackTransaction() {
        if (this.entityTransaction != null && this.entityTransaction.getEntityTransaction().isActive()) {
            this.entityTransaction.getEntityTransaction().rollback();
            this.entityTransaction = null;
        }
    }

    public abstract Class<T> getEntityClass();
}
