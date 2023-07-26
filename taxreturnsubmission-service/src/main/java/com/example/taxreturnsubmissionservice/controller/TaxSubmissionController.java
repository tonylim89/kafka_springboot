package com.example.taxreturnsubmissionservice.controller;

import com.example.basedomains.dto.TaxReturn;
import com.example.basedomains.dto.TaxReturnEvent;
import com.example.taxreturnsubmissionservice.kafka.TaxSubmissionProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1")
public class TaxSubmissionController {

    private final TaxSubmissionProducer taxSubmissionProducer;

    public TaxSubmissionController(TaxSubmissionProducer taxSubmissionProducer) {
        this.taxSubmissionProducer = taxSubmissionProducer;
    }

    @PostMapping("/submit")
    public String submitTaxReturn(@RequestBody TaxReturn taxReturn) {
        // Set event to submitted status
        TaxReturnEvent taxReturnEvent = new TaxReturnEvent();
        taxReturnEvent.setTaxReturn(taxReturn);
        taxReturnEvent.setStatus("SUBMITTED");
        taxReturnEvent.setMessage("Tax return submitted");

        taxSubmissionProducer.sendTaxReturn(taxReturnEvent);
        return "Tax return submitted successfully!";
    }
}