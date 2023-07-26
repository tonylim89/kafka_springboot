package com.example.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxReturn {
    private String taxpayerId;
    private Double income;
    @Override
    public String toString() {
        return "TaxReturn{" +
                "taxpayerId='" + taxpayerId + '\'' +
                ", income=" + income +
                '}';
    }
}
