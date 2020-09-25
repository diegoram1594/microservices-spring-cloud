package com.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue,Long> {
    Optional<ExchangeValue> findByCurrencyFormAndCurrencyTo (String currencyForm, String currencyTo);
}
