package com.creditcard.processor.repo;

import com.creditcard.processor.entity.CreditCardAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardAccountRepo extends CrudRepository<CreditCardAccount, Long> {
}
