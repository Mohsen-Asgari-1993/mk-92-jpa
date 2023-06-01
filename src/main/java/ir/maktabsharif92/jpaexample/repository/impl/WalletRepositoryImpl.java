package ir.maktabsharif92.jpaexample.repository.impl;

import ir.maktabsharif92.jpaexample.base.repository.impl.BaseEntityRepositoryImpl;
import ir.maktabsharif92.jpaexample.domain.Wallet;
import ir.maktabsharif92.jpaexample.repository.WalletRepository;

import javax.persistence.EntityManager;

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
}
