package com.example.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxReturnEvent {
    private TaxReturn taxReturn;
    private String status;
    private String message;
    private boolean processed;
}
