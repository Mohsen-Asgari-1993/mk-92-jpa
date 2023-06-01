package ir.maktabsharif92.jpaexample.service.impl;

import ir.maktabsharif92.jpaexample.base.service.BaseEntityServiceImpl;
import ir.maktabsharif92.jpaexample.domain.Wallet;
import ir.maktabsharif92.jpaexample.repository.WalletRepository;
import ir.maktabsharif92.jpaexample.service.WalletService;

public class WalletServiceImpl
        extends BaseEntityServiceImpl<Wallet, Long, WalletRepository>
        implements WalletService {

    public WalletServiceImpl(WalletRepository repository) {
        super(repository);
    }
}
