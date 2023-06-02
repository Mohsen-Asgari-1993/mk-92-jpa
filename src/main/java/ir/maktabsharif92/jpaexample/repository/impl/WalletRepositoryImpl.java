package ir.maktabsharif92.jpaexample.repository.impl;

import ir.maktabsharif92.jpaexample.base.repository.impl.BaseEntityRepositoryImpl;
import ir.maktabsharif92.jpaexample.domain.Customer;
import ir.maktabsharif92.jpaexample.domain.Wallet;
import ir.maktabsharif92.jpaexample.repository.WalletRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import java.util.List;

public class WalletRepositoryImpl
        extends BaseEntityRepositoryImpl<Wallet, Long>
        implements WalletRepository {

    public WalletRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public Class<Wallet> getEntityClass() {
        return Wallet.class;
    }

    @Override
    public List<Wallet> findAll() {
        return findAllWithEntityGraph();
    }

    private List<Wallet> findAllWithJoinFetch() {
        return em.createQuery(
                "select w from Wallet w join fetch w.customer",
                getEntityClass()
        ).getResultList();
    }

    private List<Wallet> findAllWithEntityGraph() {
        EntityGraph<?> entityGraph = em.createEntityGraph(Wallet.class);
        entityGraph.addAttributeNodes("customer");
        Subgraph<Customer> customerSub = entityGraph.addSubgraph("customer", Customer.class);
        customerSub.addAttributeNodes("addresses");

        TypedQuery<Wallet> typedQuery = em.createQuery("select w from Wallet w", getEntityClass());

        typedQuery.setHint(
                "javax.persistence.fetchgraph",
//                "javax.persistence.loadgraph",
                entityGraph
        );

        return typedQuery.getResultList();

    }
}
