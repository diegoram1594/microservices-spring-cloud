package com.microservices.currencyconversionservice;

import com.microservices.currencyconversionservice.bean.CurrencyConversionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    CurrencyExchangeServiceProxy serviceProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConversionBean> convertCurrency(@PathVariable String from, @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){
        Map<String,String> uriMap = new HashMap();
        uriMap.put("from",from);
        uriMap.put("to",to);
        ResponseEntity<CurrencyConversionBean> responseEntity= null;
        try {
            responseEntity = new RestTemplate().getForEntity(
                    "http://localhost:8000/currency-exchange/from/{from}/to/{to}"
                    ,CurrencyConversionBean.class, uriMap);
        }catch (HttpStatusCodeException exception){
            return new ResponseEntity<>(null,exception.getStatusCode());
        }

        return getCurrencyConversionBeanResponseEntity(from, to, quantity, responseEntity);
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity<CurrencyConversionBean> convertCurrencyFeign(@PathVariable String from,
                                                                       @PathVariable String to,
                                                                  @PathVariable BigDecimal quantity){
        ResponseEntity<CurrencyConversionBean> responseEntity = null;
        try {
            responseEntity = serviceProxy.retrieveExchangeValue(from, to);
        } catch (HttpStatusCodeException exception){
            return new ResponseEntity<>(null,exception.getStatusCode());
        }

        return getCurrencyConversionBeanResponseEntity(from, to, quantity, responseEntity);

    }

    private ResponseEntity<CurrencyConversionBean> getCurrencyConversionBeanResponseEntity(String from,
                                                                                           String to,
                                                                                           BigDecimal quantity,
                                                                                           ResponseEntity<CurrencyConversionBean> responseEntity) {
        CurrencyConversionBean response = responseEntity.getBody();
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            response.setFrom(from);
            response.setTo(to);
            response.setQuantity(quantity);
            response.setTotalCalculatedAmount(response.getConversionMultiple().multiply(quantity));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null,responseEntity.getStatusCode());
    }

}
