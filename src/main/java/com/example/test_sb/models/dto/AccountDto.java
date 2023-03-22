package com.example.test_sb.models.dto;

import java.math.BigDecimal;

public class AccountDto {
    private Long id;
    private BigDecimal value;
    private String name;

    public AccountDto(BigDecimal value, String name) {
        this.value = value;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
