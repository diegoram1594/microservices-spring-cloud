package com.microservices.currencyexchangeservice;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
@Entity
public class ExchangeValue {
    @Id
    @GeneratedValue
    private Long id;
    private String currencyForm;
    private String currencyTo;
    private BigDecimal conversionMultiple;
    private Integer port;



    public ExchangeValue() {
    }

    public ExchangeValue(Long id, String from, String to, BigDecimal conversionMultiple) {
        super();
        this.id = id;
        this.currencyForm = from;
        this.currencyTo = to;
        this.conversionMultiple = conversionMultiple;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyForm() {
        return currencyForm;
    }

    public void setCurrencyForm(String from) {
        this.currencyForm = from;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String to) {
        this.currencyTo = to;
    }

    public BigDecimal getConversionMultiple() {
        return conversionMultiple;
    }

    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
