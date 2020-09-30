package com.microservices.currencyconversionservice;

import com.microservices.currencyconversionservice.bean.CurrencyConversionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    public ResponseEntity<CurrencyConversionBean> retrieveExchangeValue(@PathVariable("from") String from,
                                                                        @PathVariable("to") String to);
}
